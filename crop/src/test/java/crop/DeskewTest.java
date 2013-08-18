package crop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class DeskewTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
	Deskew dsk = new Deskew();

	File inputPdfFile;
	String output;
	BufferedImage image;

	try {
//	    inputPdfFile = new File("C:\\projetCrop\\test2.pdf");
//	    output = "C:\\projetCrop\\dsk\\test2.png";
	    
//	    inputPdfFile = new File("C:\\projetCrop\\pdfTest\\AG - 300 dpi couleur.pdf");
//	    output = "C:\\projetCrop\\dsk\\AG - 300 dpi couleur.png";
	    
	    inputPdfFile = new File("C:\\projetCrop\\pdfTestNotPropre\\JC - copie n&b.pdf");
	    output = "C:\\projetCrop\\dsk\\JC - copie n&b.png";
	    
	    
	    image = getBufferedImageFromPdf(inputPdfFile);

	    CropWhitespace cropper = new CropWhitespace();
	    image = cropper.crop(image);
	    getImageFileFromBufferedImage(image, output);

	    double angle = dsk.doIt(image);
	    System.out.println("angle : " + angle);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

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

}
