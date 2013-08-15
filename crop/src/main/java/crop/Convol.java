package crop;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class Convol {

    public static BufferedImage contour(BufferedImage img) {
	 
	BufferedImage dst = new BufferedImage(img.getWidth(), img.getHeight(),
			BufferedImage.TYPE_INT_ARGB);
	// Definition du masque de convolution utilisé pour la détéction des contours de
	// l'image
	float[] mask = { -0.1F, -0.1F, -0.1F, -0.1F, 0.8F, -0.1F, -0.1F, -0.1F, -0.1F};
	Kernel kernel = new Kernel(3, 3, mask);
	// On creer notre outils de convolution
	ConvolveOp convo = new ConvolveOp(kernel);
	// On effectue la convolution
	convo.filter(img, dst);
	// On retourne l'image convoluée
	return dst;
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

    
    public static void main(String[] args) {
	File inputPdfFile = new File(
		"C:\\projetCrop\\test.pdf");

	try {
	    BufferedImage image = getBufferedImageFromPdf(inputPdfFile);
	    BufferedImage imageRes = contour(image);
	    getImageFileFromBufferedImage(imageRes,
			"C:\\projetCrop\\pdfTest\\resultConvolTest.png");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
