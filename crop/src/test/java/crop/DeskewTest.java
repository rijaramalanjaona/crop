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
		// File inputFile = new File("C:\\projetCrop\\outputPngFile.png");
		//
		// BufferedImage image;
		//
		// try {
		// image = ImageIO.read(inputFile);
		// double angle = dsk.doIt(image);
		// System.out.println("angle : " + angle);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		File inputPdfFile = new File("C:\\projetCrop\\test.pdf");
		BufferedImage image;

		try {
			image = getBufferedImageFromPdf(inputPdfFile);

			// image = CopyOfCropImage.trimNoise(image);
			// getImageFileFromBufferedImage(image,
			// "C:\\projetCrop\\afterTrimNoisetest.png");
			//
			// image = CopyOfCropImage.trimWhite(image);
			// getImageFileFromBufferedImage(image,
			// "C:\\projetCrop\\afterTrimWhitetest.png");

			double angle = dsk.doIt(image);
			System.out.println("angle : " + angle);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static BufferedImage getBufferedImageFromPdf(File pdfFile) throws IOException {
		PDDocument docIn = null;
		BufferedImage image = null;
		try {
			// Read the pdf into a BufferedImage
			docIn = PDDocument.load(pdfFile);
			List<PDPage> pages = docIn.getDocumentCatalog().getAllPages();
			PDPage page = pages.get(0);

			// get the image
			image = page.convertToImage(BufferedImage.TYPE_INT_RGB, 300);

			System.out.println("image width : " + image.getWidth() + " height : " + image.getHeight());

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
	public static File getImageFileFromBufferedImage(BufferedImage bufferedImage, String pathOutPut) {
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
