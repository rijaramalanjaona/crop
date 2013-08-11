package crop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class CornersTest {

    public static void main(String[] args) {
	System.out.println("------------------ CornersTest -----------------");

	File inputPdfFile = new File("C:\\projetCrop\\test.pdf"); // pdf en
								  // entree

	BufferedImage image = getBufferedImageFromPdf(inputPdfFile);
	if (image != null) {
	    Corners corners = new Corners(image);

	    System.out.println("Affichage corners");
	    System.out.println("Found top corner : " + corners.getTop());
	    System.out.println("Found right corner : " + corners.getRight());
	    System.out.println("Found bottom corner : " + corners.getBottom());
	    System.out.println("Found left corner : " + corners.getLeft());

	    /*
	     * 	taille de l'image 2550 * 3504
	     * 
	     * 	Found top corner : 1583, 0
                Found right corner : 2549, 868
                Found bottom corner : 43, 3503
                Found left corner : 0, 10
	     */
	    
	    // TODO a revoir findCorner
	}

    }

    @SuppressWarnings("unchecked")
    public static BufferedImage getBufferedImageFromPdf(File pdfFile) {
	PDDocument docIn = null;
	BufferedImage image = null;
	try {
	    // Read the pdf into a BufferedImage
	    docIn = PDDocument.load(pdfFile);
	    List<PDPage> pages = docIn.getDocumentCatalog().getAllPages();
	    PDPage page = pages.get(0);
	    System.out.println("nb pages dans le pdf : " + pages.size());

	    // get the image
	    image = page.convertToImage(BufferedImage.TYPE_INT_RGB, 300);

	    System.out.println("image width : " + image.getWidth()
		    + " height : " + image.getHeight());

	} catch (IOException e) {
	    e.printStackTrace();
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

}
