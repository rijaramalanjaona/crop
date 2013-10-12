package crop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Classe utilisee pour la detection du type de piece justificative. recupere
 * les fichiers txt dans le dossier out de l'OCR. se base sur la recherche de
 * mots cles. la comparaison de chaines de caracteres se fait avec l'algorithme
 * de la distance de Levenshtein.
 * 
 */
public class DetectionTypePJ {

	private static final String DIR_OCR_OUT = "C:\\abbyy\\pj\\out\\";

	private static final int NB_MIN_CARACTERES_MOT_INTERESSANT = 4;
	private static final double SEUIL_RESSEMBLANCE = 0.7;

	private static String[] motsCNIRecto = { "republique francaise", "carte nationale d'identite",
			"nationalite francaise", "idfra" };

	private static String[] motsCNIVerso = { "adresse", "carte valable jusqu'au", "delivree le",
			"signature de l'autorite" };

	private static String[] motsPasseport = { "republique française", "passeport", "signature du titulaire",
			"page reservee aux autorites competentes pour", "pages reserved for issuing authorities",
			"les enfants ayant atteint" };

	private static String[] motsPermis = { "permis", "categories de vehicules", "pour lesquels le permis est valable",
			"permis delivre a titre" };

	public static String getTextFromOCR(File file) throws Exception {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line = null;
		while ((line = reader.readLine()) != null) {
			result.append(line).append('\n');
		}
		reader.close();
		return normalizeText(result.toString());
	}

	public static String processDetectType(String text) {
		String type = "inconnu";
		String[] listeMotsTexte = Pattern.compile("[^a-z0123456789 ']", Pattern.CASE_INSENSITIVE).split(text.trim(), 0);
		Set<String> motsInteressants = new HashSet<String>();
		for (String mot : listeMotsTexte) {
			if (mot.length() >= NB_MIN_CARACTERES_MOT_INTERESSANT) {
				motsInteressants.add(mot.trim());
				// System.out.println(mot);
			}
		}
		Map<String, String[]> listeTypesDocuments = new HashMap<String, String[]>();
		listeTypesDocuments.put("CNI Recto", motsCNIRecto);
		listeTypesDocuments.put("CNI Verso", motsCNIVerso);
		listeTypesDocuments.put("Passeport", motsPasseport);
		listeTypesDocuments.put("Permis de conduire", motsPermis);

		Map<String, Integer> documentsReconnus = new TreeMap<String, Integer>();

		for (Entry<String, String[]> typeCompare : listeTypesDocuments.entrySet()) {
			int i = 0;
			for (String motReference : typeCompare.getValue()) {

				for (String termeScan : motsInteressants) {
					double ressemblance = comparaison(motReference, termeScan);
					// System.out.println("resemblance : " + ressemblance +
					// " #motReference : " + motReference
					// + " #termeScan : " + termeScan);
					if (ressemblance > SEUIL_RESSEMBLANCE) {
						i++;
					}
					if (termeScan.toLowerCase().contains(motReference)) {
						i++;
					}
				}
			}
			if (i > 0) {
				documentsReconnus.put(typeCompare.getKey(), i);
			}
		}
		int max = 0;
		for (Entry<String, Integer> e : documentsReconnus.entrySet()) {
			// System.out.println(e.getKey() + " : " + e.getValue());
			if (max < e.getValue()) {
				max = e.getValue();
				type = e.getKey();
			}
		}

		return type;
	}

	public static double comparaison(String termeRef, String termeInconnu) {
		// calcul de la différence de caracteres
		double diffCaract = (1.0 - (Double.valueOf(computeLevenshteinDistance(termeInconnu.toUpperCase(),
				termeRef.toUpperCase())
				/ Double.valueOf(Math.max(termeRef.length(), termeInconnu.length())))));

		return diffCaract;
	}

	public static int computeLevenshteinDistance(CharSequence str1, CharSequence str2) {
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i <= str1.length(); i++) {
			distance[i][0] = i;
		}
		for (int j = 0; j <= str2.length(); j++) {
			distance[0][j] = j;
		}

		for (int i = 1; i <= str1.length(); i++) {
			for (int j = 1; j <= str2.length(); j++) {
				distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1),
						distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
			}
		}

		return distance[str1.length()][str2.length()];
	}

	public static String normalizeText(String texte) {
		StringBuffer sb = new StringBuffer();
		String texteNormalise = Normalizer.normalize(texte, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
		for (Character c : texteNormalise.toCharArray()) {
			switch (Character.getType(c)) {

			case Character.UPPERCASE_LETTER:
			case Character.LOWERCASE_LETTER:
			case Character.DECIMAL_DIGIT_NUMBER:
			case Character.LETTER_NUMBER:
			case Character.TITLECASE_LETTER:
				sb.append(c);
				break;

			case Character.FINAL_QUOTE_PUNCTUATION:
			case Character.INITIAL_QUOTE_PUNCTUATION:
				sb.append('\'');
				break;

			case Character.PARAGRAPH_SEPARATOR:
			case Character.LINE_SEPARATOR:
			case Character.CONTROL:
				sb.append('\n');
				break;

			case Character.COMBINING_SPACING_MARK:
			case Character.CONNECTOR_PUNCTUATION:
			case Character.CURRENCY_SYMBOL:
			case Character.DASH_PUNCTUATION:
			case Character.ENCLOSING_MARK:
			case Character.END_PUNCTUATION:
			case Character.FORMAT:
			case Character.MATH_SYMBOL:
			case Character.MODIFIER_LETTER:
			case Character.MODIFIER_SYMBOL:
			case Character.NON_SPACING_MARK:
			case Character.OTHER_LETTER:
			case Character.OTHER_NUMBER:
			case Character.OTHER_PUNCTUATION:
			case Character.OTHER_SYMBOL:
			case Character.PRIVATE_USE:
			case Character.SPACE_SEPARATOR:
			case Character.START_PUNCTUATION:
			case Character.SURROGATE:
			case Character.UNASSIGNED:
				sb.append(' ');
				break;

			default:
				sb.append(' ');
				break;
			}
		}
		return sb.toString().replaceAll("  ", " ");
	}

	public static void main(String[] args) throws Exception {
		File rep = new File(DIR_OCR_OUT);
		for (File file : rep.listFiles()) {
			String text = DetectionTypePJ.getTextFromOCR(file);
			String type = DetectionTypePJ.processDetectType(text);
			System.out.println(file.getName() + ";" + type);
		}
	}

}
