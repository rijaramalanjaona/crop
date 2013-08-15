package kmeans;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;


public class KMeansTest {
    static int[] histogram;

    public static void main(String[] args) throws IOException {
	System.out.println("------------------ KMeansTest -----------------");

	File inputPdfFile = new File(
		"C:\\projetCrop\\test.pdf");

	BufferedImage image = getBufferedImageFromPdf(inputPdfFile);

	histogram = new int[256];
	createGrayTotals(image);
	KMeansAction kma = new KMeansAction(image, 256, histogram,
		KMeansAction.MEAN_BY_MOD);
	BufferedImage imageResult = kma.getResultImage();
	getImageFileFromBufferedImage(imageResult,
		"C:\\projetCrop\\pdfTest\\resultKmeansTest.png");
    }

    @SuppressWarnings("unchecked")
    public static BufferedImage getBufferedImageFromPdf(File pdfFile)
	    throws IOException {
	PDDocument docIn = null;
	BufferedImage image = null;
	try {
	    // Read the pdf into a BufferedImage
	    docIn = PDDocument.load(pdfFile);
	    List<PDPage> pages = docIn.getDocumentCatalog().getAllPages();
	    PDPage page = pages.get(0);

	    // get the image
	    image = page.convertToImage(BufferedImage.TYPE_INT_RGB, 300);

	    System.out.println("image width : " + image.getWidth()
		    + " height : " + image.getHeight());

	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (docIn != null) {
		docIn.close();
	    }
	}
	return image;
    }

    // generer une image png correpsondante au pdf
    public static File getImageFileFromBufferedImage(
	    BufferedImage bufferedImage, String pathOutPut) {
	File imageFile = null;
	try {
	    imageFile = new File(pathOutPut);
	    ImageIO.write(bufferedImage, "png", imageFile);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return imageFile;
    }

    public static void createGrayTotals(BufferedImage image) {
	for (int i = 0; i < 256; i++) {
		histogram[i] = 0;
	}
	
	int height = image.getHeight();
	int width = image.getWidth();
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		Color color = new Color(image.getRGB(x, y));
		int greyvalue = color.getRed();
		histogram[greyvalue] += 1;
	    }
	}
    }
}
