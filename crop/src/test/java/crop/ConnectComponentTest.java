package crop;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class ConnectComponentTest {

    public static void main(String[] args) throws IOException {
	System.out
		.println("------------------ ConnectComponentTest -----------------");

	File inputPdfFile = new File(
		"C:\\projetCrop\\pdfTest\\AG - copie n&b.pdf");

	BufferedImage image = getBufferedImageFromPdf(inputPdfFile);
	Dimension dimension = new Dimension();
	dimension.setSize(image.getWidth(), image.getHeight());

	ConnectComponent cc = new ConnectComponent();
	int[] tblImg = cc.createGrayTotals(image);
	int[] tblLabels = cc.compactLabeling(tblImg, dimension, true);

	System.out.println("affichage tblLabels");
	for (int label : tblLabels) {
	    System.out.println("label : " + label);
	}

	System.out
		.println("------------------ Fin ConnectComponentTest -----------------");

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

}
