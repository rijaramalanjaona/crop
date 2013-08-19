package crop;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

public class AutoCropRotate {
	private static final int MAX_WIDTH_PDF = 500;
	private static final double MAX_SCALED_DOWN_WIDTH = 500.0;

	private File inputPdfFile;
	private File outputPngFile;
	private File outputScaledPngFile;
	private File outputPdfFile;

	// private static final int MARGE = 0;

	/** Prepare the test files. */
	private void init() throws Exception {
		// inputPdfFile = new File("C:\\projetCrop\\pdfTest\\test2.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\outputPngFile2.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\outputScaledPngFile2.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\outputPdfFile2.pdf");

		// AG - 300 dpi couleur.pdf
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\AG - 300 dpi couleur.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\AG - 300 dpi couleur-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\AG - 300 dpi couleur-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\AG - 300 dpi couleur-outputPdfFile.pdf");

		// AG - 300 dpi n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\AG - 300 dpi n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\AG - 300 dpi n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\AG - 300 dpi n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\AG - 300 dpi n&b-outputPdfFile.pdf");

		// AG - copie n&b.pdf
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\AG - copie n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\AG - copie n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\AG - copie n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\AG - copie n&b-outputPdfFile.pdf");

		// AH - 300 dpi couleur.pdf
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\AH - 300 dpi couleur.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\AH - 300 dpi couleur-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\AH - 300 dpi couleur-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\AH - 300 dpi couleur-outputPdfFile.pdf");

		// AH - 300 dpi n&b.pdf
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\AH - 300 dpi n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\AH - 300 dpi n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\AH - 300 dpi n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\AH - 300 dpi n&b-outputPdfFile.pdf");
		//
		// // AH - copie n&b.pdf
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\AH - copie n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\AH - copie n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\AH - copie n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\AH - copie n&b-outputPdfFile.pdf");

		// // AP - 300 dpi couleur.pdf
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\AP - 300 dpi couleur.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\AP - 300 dpi couleur-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\AP - 300 dpi couleur-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\AP - 300 dpi couleur-outputPdfFile.pdf");

		// AP - 300 dpi n&b.pdf
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\AP - 300 dpi n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\AP - 300 dpi n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\AP - 300 dpi n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\AP - 300 dpi n&b-outputPdfFile.pdf");

		// AP - copie n&b.pdf
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\AP - copie n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\AP - copie n&bb-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\AP - copie n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\AP - copie n&b-outputPdfFile.pdf");

		// BR - 300 dpi couleur
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\BR - 300 dpi couleur.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\BR - 300 dpi couleur-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\BR - 300 dpi couleur-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\BR - 300 dpi couleur-outputPdfFile.pdf");

		// BR - 300 dpi n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\BR - 300 dpi n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\BR - 300 dpi n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\BR - 300 dpi n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\BR - 300 dpi n&b-outputPdfFile.pdf");

		// BR - copie n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\BR - copie n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\BR - copie n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\BR - copie n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\BR - copie n&b-outputPdfFile.pdf");

		// // JC - 300 dpi couleur
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\JC - 300 dpi couleur.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\JC - 300 dpi couleur-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\JC - 300 dpi couleur-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\JC - 300 dpi couleur-outputPdfFile.pdf");

		// // JC - 300 dpi n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\JC - 300 dpi n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\JC - 300 dpi n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\JC - 300 dpi n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\JC - 300 dpi n&b-outputPdfFile.pdf");

		// JC - copie n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\JC - copie n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\JC - copie n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\JC - copie n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\JC - copie n&b-outputPdfFile.pdf");

		// MB - 300 dpi couleur
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\MB - 300 dpi couleur.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\MB - 300 dpi couleur-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\MB - 300 dpi couleur-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\MB - 300 dpi couleur-outputPdfFile.pdf");

		// MB - 300 dpi n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\MB - 300 dpi n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\MB - 300 dpi n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\MB - 300 dpi n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\MB - 300 dpi n&b-outputPdfFile.pdf");

		// MB - copie n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\MB - copie n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\MB - copie n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\MB - copie n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\MB - copie n&b-outputPdfFile.pdf");

		// NM - 300 dpi couleur
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\NM - 300 dpi couleur.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\NM - 300 dpi couleur-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\NM - 300 dpi couleur-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\NM - 300 dpi couleur-outputPdfFile.pdf");

		// NM - 300 dpi n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\NM - 300 dpi n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\NM - 300 dpi n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\NM - 300 dpi n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\NM - 300 dpi n&b-outputPdfFile.pdf");

		// // NM - copie n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\NM - copie n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\NM - copie n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\NM - copie n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\NM - copie n&b-outputPdfFile.pdf");

		// // VF - 300 dpi couleur
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\VF - 300 dpi couleur.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\VF - 300 dpi couleur-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\VF - 300 dpi couleur-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\VF - 300 dpi couleur-outputPdfFile.pdf");

		// VF - 300 dpi n&b
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTest\\VF - 300 dpi n&b.pdf");
		// outputPngFile = new
		// File("C:\\projetCrop\\outputPng\\VF - 300 dpi n&b-outputPngFile.png");
		// outputScaledPngFile = new
		// File("C:\\projetCrop\\outputScaledPng\\VF - 300 dpi n&b-outputScaledPngFile.png");
		// outputPdfFile = new
		// File("C:\\projetCrop\\pdfResult\\VF - 300 dpi n&b-outputPdfFile.pdf");
		//
		// VF - copie n&b
		inputPdfFile = new File("C:\\projetCrop\\pdfTest\\VF - copie n&b.pdf");
		outputPngFile = new File("C:\\projetCrop\\outputPng\\VF - copie n&b-outputPngFile.png");
		outputScaledPngFile = new File("C:\\projetCrop\\outputScaledPng\\VF - copie n&b-outputScaledPngFile.png");
		outputPdfFile = new File("C:\\projetCrop\\pdfResult\\VF - copie n&b-outputPdfFile.pdf");

	}

	/** Go. */
	@SuppressWarnings("unchecked")
	private void go() throws Exception {
		init();

		PDDocument docIn = null;
		Document docOut = null;
		PdfWriter writerOut = null;
		try {
			// Read the pdf into a BufferedImage
			docIn = PDDocument.load(inputPdfFile);
			List<PDPage> pages = docIn.getDocumentCatalog().getAllPages();
			PDPage page = pages.get(0);
			BufferedImage image = page.convertToImage(BufferedImage.TYPE_INT_RGB, 300);

			System.out.println("img size : " + image.getWidth() + " * " + image.getHeight());

			// Automatically crop the image excluding any bad edges
			CropWhitespace cropper = new CropWhitespace(); // TODO a revoir
			// image = cropper.cropPure(image, MARGE, MARGE, MARGE, MARGE);

			image = cropper.crop(image); // TODO eliminer les noises

			// TODO a revoir
			AngleDetector angler = new AngleDetector();
			double angle0 = angler.getRotation(image);

			// TODO avec DSK
			Deskew dsk = new Deskew();
			double angle = dsk.doIt(image);

			System.out.println("angle dsk = " + angle + " angle corner methode : " + angle0);

			// TODO DELETE ME once AngleDetector works
			// angle = 2;

			// debug : to visually double check the image
			ImageIO.write(image, "png", outputPngFile);

			// Scale down via Java to reduce file size
			int w = image.getWidth();
			int h = image.getHeight();
			int largestDimension = Math.max(w, h);
			double scale = MAX_SCALED_DOWN_WIDTH / largestDimension;
			BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scale, scale);
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			image = scaleOp.filter(image, scaled);
			int rightEdge = image.getWidth() - (int) (image.getWidth() * scale);
			int bottomEdge = image.getHeight() - (int) (image.getHeight() * scale);
			image = cropper.cropPure(image, 0, rightEdge, bottomEdge, 0);

			// debug : to visually double check the image
			ImageIO.write(image, "png", outputScaledPngFile);

			// write out the image to PDF
			FileOutputStream outputStream = new FileOutputStream(outputPdfFile);
			docOut = new Document();
			writerOut = PdfWriter.getInstance(docOut, outputStream);
			writerOut.setFullCompression();
			docOut.open();
			Image pdfImage = Image.getInstance(writerOut, image, 1.0f);
			pdfImage.setRotationDegrees(Double.valueOf(angle).floatValue());
			if (Math.max(image.getWidth(), image.getHeight()) > MAX_WIDTH_PDF) {
				pdfImage.scaleToFit(MAX_WIDTH_PDF, MAX_WIDTH_PDF);
			}
			docOut.add(pdfImage);

		} finally {
			if (docIn != null) {
				docIn.close();
			}
			if (docOut != null) {
				docOut.close();
			}
			if (writerOut != null) {
				writerOut.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new AutoCropRotate().go();
		System.out.println("fini");
		// System.out.println(new GrayColor(80, 160, 255).getGrayscale());
		// System.out.println(new GrayColor(80, 160,
		// 255).getGrayscaleLuminance());
		// System.out.println(new GrayColor(160, 255, 80).getGrayscale());
		// System.out.println(new GrayColor(160, 255,
		// 80).getGrayscaleLuminance());
		// System.out.println(new GrayColor(255, 80, 160).getGrayscale());
		// System.out.println(new GrayColor(255, 80,
		// 160).getGrayscaleLuminance()); TODO what for?
	}

}
