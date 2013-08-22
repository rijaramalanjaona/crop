package crop;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

public class AutoCropAndRotate {
    private static final int MAX_WIDTH_PDF = 500;
    private static final double MAX_SCALED_DOWN_WIDTH = 500.0;

    private static final String DIR_PDF_TEST = "C:\\projetCrop\\pdfTest\\";
    private static final String DIR_PDF_RESULT = "C:\\projetCrop\\pdfResult\\";

    private File inputPdfFile;

    public AutoCropAndRotate(File inputPdfFile) {
	this.inputPdfFile = inputPdfFile;
    }

    @SuppressWarnings("unchecked")
    private void go() throws Exception {

	List<PDPage> pages;
	PDDocument docIn = null;

	try {
	    // Read the pdf into a BufferedImage
	    docIn = PDDocument.load(inputPdfFile);
	    pages = docIn.getDocumentCatalog().getAllPages();

	    BufferedImage currentImage;
	    String currentFileName = null;
	    int numPage = 0;

	    for (PDPage currentPage : pages) {

		numPage++;
		currentFileName = StringUtils.substringBefore(
			inputPdfFile.getName(), ".pdf");
		System.out.println(">>>>>>>>>> Debut traitement de : "
			+ inputPdfFile.getName() + " page " + numPage);

		currentImage = currentPage.convertToImage(
			BufferedImage.TYPE_INT_RGB, 300);
		System.out.println("img size : " + currentImage.getWidth()
			+ " * " + currentImage.getHeight());

		// Automatically crop the image excluding any bad edges
		CropWhitespace cropper = new CropWhitespace();
		currentImage = cropper.crop(currentImage);

		// Segmentation au cas ou il y aurait le recto et le verso dans
		// la meme page
		Segmentation segmentation = new Segmentation(currentImage);
		segmentation.go();

		if (segmentation.getListeSousImages().size() == 1) {
		    System.out.println(">>> pas de sous image ");

		    File outputPdfFile = new File(DIR_PDF_RESULT
			    + currentFileName + "-page" + numPage
			    + "-outputPdfFile.pdf");

		    transforme(currentImage, cropper, outputPdfFile);

		} else if (segmentation.getListeSousImages().size() == 2) {
		    System.out.println(">>> 2 sous images ");
		    int numSousImage = 1;
		    for (BufferedImage sousImage : segmentation
			    .getListeSousImages()) {
			System.out.println(">>> debut traitement sous image "
				+ numSousImage + " " + inputPdfFile.getName()
				+ " page " + numPage);

			// crop
			sousImage = cropper.crop(sousImage);

			File outputPdfFile = new File(DIR_PDF_RESULT
				+ currentFileName + "-page" + numPage
				+ "-sousImage-" + numSousImage
				+ "-outputPdfFile.pdf");

			transforme(sousImage, cropper, outputPdfFile);

			System.out.println(">>> fin traitement sous image "
				+ numSousImage + " " + inputPdfFile.getName()
				+ " page " + numPage);
			numSousImage++;

		    }
		}

		System.out.println(">>>>>>>>>> Fin traitement de : "
			+ inputPdfFile.getName() + " page " + numPage);

	    }

	} catch (Exception e) {
	    System.out.println("Erreur dans go() : " + e);
	} finally {
	    if (docIn != null) {
		docIn.close();
	    }
	}
    }

    /**
     * Calcul de l'angle de rotation, Rotation de l'image, Ecriture dans le pdf
     * 
     * @param image
     * @param cropper
     * @param outputPngFile
     * @param outputScaledPngFile
     * @param outputPdfFile
     */
    public void transforme(BufferedImage image, CropWhitespace cropper,
	    File outputPdfFile) {
	Document docOut = null;
	PdfWriter writerOut = null;

	try {
	    // Deskew image
	    Deskew dsk = new Deskew();
	    double angle = dsk.doIt(image);
	    System.out.println("angle = " + angle);

	    // Scale down via Java to reduce file size
	    int w = image.getWidth();
	    int h = image.getHeight();
	    int largestDimension = Math.max(w, h);
	    double scale = MAX_SCALED_DOWN_WIDTH / largestDimension;
	    BufferedImage scaled = new BufferedImage(w, h,
		    BufferedImage.TYPE_INT_ARGB);
	    AffineTransform at = new AffineTransform();
	    at.scale(scale, scale);
	    AffineTransformOp scaleOp = new AffineTransformOp(at,
		    AffineTransformOp.TYPE_BILINEAR);
	    image = scaleOp.filter(image, scaled);
	    int rightEdge = image.getWidth() - (int) (image.getWidth() * scale);
	    int bottomEdge = image.getHeight()
		    - (int) (image.getHeight() * scale);

	    image = cropper.cropPure(image, 0, rightEdge, bottomEdge, 0);

	    // write out the image to PDF
	    FileOutputStream outputStream = new FileOutputStream(outputPdfFile);

	    docOut = new Document();
	    writerOut = PdfWriter.getInstance(docOut, outputStream);
	    writerOut.setFullCompression();
	    docOut.open();

	    Image pdfImage = Image.getInstance(writerOut, image, 1.0f);
	    // check for long side or short side
	    if (w < h) {
		// CNI add 90 degrees
		angle += 90;
	    }
	    pdfImage.setRotationDegrees(Double.valueOf(angle).floatValue());

	    if (Math.max(image.getWidth(), image.getHeight()) > MAX_WIDTH_PDF) {
		pdfImage.scaleToFit(MAX_WIDTH_PDF, MAX_WIDTH_PDF);
	    }

	    docOut.add(pdfImage);

	} catch (Exception e) {
	    System.out.println("Erreur dans transforme >>> " + e);
	    e.printStackTrace();
	} finally {
	    if (docOut != null) {
		docOut.close();
	    }
	    if (writerOut != null) {
		writerOut.close();
	    }
	}
    }

    public static void main(String[] args) throws Exception {
	File pdf = new File(DIR_PDF_TEST
		+ "BR - 300 dpi couleur.pdf");

	AutoCropAndRotate autoCropAndRotate = new AutoCropAndRotate(pdf);
	autoCropAndRotate.go();
	System.out.println("finished");
    }

}
