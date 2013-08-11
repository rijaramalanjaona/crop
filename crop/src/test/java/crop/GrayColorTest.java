package crop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class GrayColorTest {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
	System.out
		.println("------------------ GrayColorTest -----------------");
	System.out
		.println("test de la methode getGrayscale() utilisee dans Corners initEdges() + generation img correspondante au scan");

	PDDocument docIn = null;
	File inputPdfFile = new File("C:\\projetCrop\\test.pdf"); // pdf en entree
	File output = new File("C:\\projetCrop\\testOutput.png"); // image correspondante
	
	int width, height;
	int resultatGetGrayscale;

	try {
	    // Read the pdf into a BufferedImage
	    docIn = PDDocument.load(inputPdfFile);
	    List<PDPage> pages = docIn.getDocumentCatalog().getAllPages();
	    PDPage page = pages.get(0);
	    System.out.println("nb pages dans le pdf : " + pages.size());

	    // get the image
	    BufferedImage image = page.convertToImage(
		    BufferedImage.TYPE_INT_RGB, 300);

	    width = image.getWidth();
	    height = image.getHeight();
	    System.out
		    .println("image width : " + width + " height : " + height);

	    // generation de l'image png correpsondante à la page 1
	    System.out.println("Generation de l'image correspondante...");
	    ImageIO.write(image, "png", output);
	    
	    // appel de la méthode getGrayscale() pour un point quelconque
	    resultatGetGrayscale = new GrayColor(image.getRGB(1750, 800))
		    .getGrayscale();
	    System.out.println("resultatGetGrayscale 1 : "
		    + resultatGetGrayscale);

	    
	    resultatGetGrayscale = new GrayColor(image.getRGB(0, 450))
		    .getGrayscale();
	    System.out.println("resultatGetGrayscale 2 : "
		    + resultatGetGrayscale);
	    
	    

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

}
