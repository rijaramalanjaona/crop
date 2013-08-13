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
    private static final int MAX_WIDTH_PDF = 500; // pkw 500?
    private static final double MAX_SCALED_DOWN_WIDTH = 500.0;

    private File inputPdfFile;
    private File outputPngFile;
    private File outputScaledPngFile;
    private File outputPdfFile;

    // private static final int MARGE = 0;

    /** Prepare the test files. */
    private void init() throws Exception {
	 inputPdfFile = new File("C:\\projetCrop\\test.pdf");
	 outputPngFile = new File("C:\\projetCrop\\outputPngFile.png");
	 outputScaledPngFile = new
	 File("C:\\projetCrop\\outputScaledPngFile.png");
	 outputPdfFile = new File("C:\\projetCrop\\outputPdfFile.pdf");

	// AG - 300 dpi couleur.pdf
//	inputPdfFile = new File(
//		"C:\\projetCrop\\pdfTest\\AG - 300 dpi couleur.pdf");
//	outputPngFile = new File(
//		"C:\\projetCrop\\outputPng\\AG - 300 dpi couleur-outputPngFile.png");
//	outputScaledPngFile = new File(
//		"C:\\projetCrop\\outputScaledPng\\AG - 300 dpi couleur-outputScaledPngFile.png");
//	outputPdfFile = new File(
//		"C:\\projetCrop\\pdfResult\\AG - 300 dpi couleur-outputPdfFile.pdf");

	// AG - copie n&b.pdf
	// inputPdfFile = new
	// File("C:\\projetCrop\\pdfTest\\AG - copie n&b.pdf");
	// outputPngFile = new
	// File("C:\\projetCrop\\outputPng\\AG - copie n&b-outputPngFile.png");
	// outputScaledPngFile = new
	// File("C:\\projetCrop\\outputScaledPng\\AG - copie n&b-outputScaledPngFile.png");
	// outputPdfFile = new
	// File("C:\\projetCrop\\pdfResult\\AG - copie n&b-outputPdfFile.pdf");

	// MB - copie n&b.pdf
//	 inputPdfFile = new
//	 File("C:\\projetCrop\\pdfTest\\MB - copie n&b-2.pdf");
//	 outputPngFile = new
//	 File("C:\\projetCrop\\outputPng\\MB - copie n&b-2-outputPngFile.png");
//	 outputScaledPngFile = new
//	 File("C:\\projetCrop\\outputScaledPng\\MB - copie n&b-2-outputScaledPngFile.png");
//	 outputPdfFile = new
//	 File("C:\\projetCrop\\pdfResult\\MB - copie n&b-2-outputPdfFile.pdf");
	
//	inputPdfFile = new File(
//		"C:\\projetCrop\\pdfTest\\cni1.pdf");
//	outputPngFile = new File(
//		"C:\\projetCrop\\outputPng\\cni1.png");
//	outputScaledPngFile = new File(
//		"C:\\projetCrop\\outputScaledPng\\cni1.png");
//	outputPdfFile = new File(
//		"C:\\projetCrop\\pdfResult\\cni1.pdf");
	 
//		inputPdfFile = new File(
//		"C:\\projetCrop\\pdfTest\\cni2.pdf");
//	outputPngFile = new File(
//		"C:\\projetCrop\\outputPng\\cni2.png");
//	outputScaledPngFile = new File(
//		"C:\\projetCrop\\outputScaledPng\\cni2.png");
//	outputPdfFile = new File(
//		"C:\\projetCrop\\pdfResult\\cni2.pdf");
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
	    BufferedImage image = page.convertToImage(
		    BufferedImage.TYPE_INT_RGB, 300);

	    // Automatically crop the image excluding any bad edges
	    CropWhitespace cropper = new CropWhitespace(); // TODO a revoir
	    // image = cropper.cropPure(image, MARGE, MARGE, MARGE, MARGE);
	    image = cropper.crop(image); // TODO testé, qd le bord haut de
	    // l'image est collé en haut de
	    // page, y a pas de crop => Unable
	    // to crop image. Returning original

	    // TODO a revoir
	    AngleDetector angler = new AngleDetector();
	    double angle = angler.getRotation(image);
	    System.out.println("angle revu = " + angle);

	    // TODO DELETE ME once AngleDetector works
	    // angle = 2;

	    // debug : to visually double check the image
	    ImageIO.write(image, "png", outputPngFile);

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

	    // debug : to visually double check the image
	    ImageIO.write(image, "png", outputScaledPngFile); // TODO ca genere
							      // bien une
							      // image reduite

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
	    docOut.add(pdfImage); // TODO ca ecrit bien dans le pdf avec l'angle
				  // de rotation 92

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
