package crop;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

public class AutoCropRotate {
    private static final String SQUARE_FILENAME = "Square.png";
    private static final int MAX_WIDTH_PDF = 500;
    private static final double MAX_SCALED_DOWN_WIDTH = 500.0;

    private File inputPdfFile;
    private File outputPngFile;
    private File outputScaledPngFile;
    private File outputPdfFile;

    // private static final int MARGE = 0;

    /** Prepare the test files. */
    private void init() throws Exception {
	URL url = getClass().getResource(SQUARE_FILENAME);
	URI uri = new URI(url.toString());
	File squareFile = new File(uri);

	// InputStream imageIn = new FileInputStream(squareFile);
	// BufferedImage image = ImageIO.read(imageIn);

	File parentFile = new File(squareFile.getParent().replace("\\bin\\",
		"\\src\\"));
	inputPdfFile = new File(parentFile, "test.pdf");
	outputPngFile = new File(parentFile, "Trimmed.png");
	outputScaledPngFile = new File(parentFile, "Scaled.png");
	outputPdfFile = new File(parentFile, "Trimmed.pdf");
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
	    CropWhitespace cropper = new CropWhitespace();
	    // image = cropper.cropPure(image, MARGE, MARGE, MARGE, MARGE);
	    image = cropper.crop(image);
	    AngleDetector angler = new AngleDetector();
	    double angle = angler.getRotation(image);
	    System.out.println("angle=" + angle);

	    // TODO DELETE ME once AngleDetector works
	    angle = 92;

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
	// 160).getGrayscaleLuminance());
    }

}
