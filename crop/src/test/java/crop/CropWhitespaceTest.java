package crop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class CropWhitespaceTest {

	public static void main(String[] args) throws IOException {
		System.out.println("------------------ CornersTest -----------------");

		File inputPdfFile;
		String output;
		String avantCrop;

		// inputPdfFile = new File("C:\\projetCrop\\pdfTestPropre\\test2.pdf");
		// output = "C:\\projetCrop\\pdfTestPropre\\pngOutput\\test2-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\test2-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestPropre\\AG - 300 dpi couleur.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\AG - 300 dpi couleur-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\AG - 300 dpi couleur-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestPropre\\AH - 300 dpi couleur.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\AH - 300 dpi couleur-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\AH - 300 dpi couleur-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestPropre\\AP - 300 dpi couleur.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\AP - 300 dpi couleur-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\AP - 300 dpi couleur-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestPropre\\BR - 300 dpi couleur.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\BR - 300 dpi couleur-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\BR - 300 dpi couleur-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestPropre\\JC - 300 dpi couleur.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\JC - 300 dpi couleur-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\JC - 300 dpi couleur-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestPropre\\MB - 300 dpi couleur.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\MB - 300 dpi couleur-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\MB - 300 dpi couleur-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestPropre\\NM - 300 dpi couleur.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\NM - 300 dpi couleur-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\NM - 300 dpi couleur-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestPropre\\VF - 300 dpi couleur.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\VF - 300 dpi couleur-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\VF - 300 dpi couleur-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestPropre\\MB - copie n&b-2.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\MB - copie n&b-2-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestPropre\\pngOutput\\MB - 300 dpi couleur-AvantCrop.png";
		// //
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\MB - copie n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\MB - copie n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\MB - 300 dpi couleur-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\AG - copie n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AG - copie n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AG - copie n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\AH - copie n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AH - copie n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AH - copie n&b-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\AP - copie n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AP - copie n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AP - copie n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\BR - copie n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\BR - copie n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\BR - copie n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\JC - copie n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\JC - copie n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\JC - copie n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\NM - copie n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\NM - copie n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\NM - copie n&b-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\VF - copie n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\VF - copie n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\VF - copie n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\MB - 300 dpi n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\MB - 300 dpi n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\MB - 300 dpi n&b-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\AG - 300 dpi n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AG - 300 dpi n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AG - 300 dpi n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\AH - 300 dpi n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AH - 300 dpi n&b.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AH - 300 dpi n&b-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\AP - 300 dpi n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AP - 300 dpi n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\AP - 300 dpi n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\BR - 300 dpi n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\BR - 300 dpi n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\BR - 300 dpi n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\JC - 300 dpi n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\JC - 300 dpi n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\JC - 300 dpi n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\NM - 300 dpi n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\NM - 300 dpi n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\NM - 300 dpi n&b-AvantCrop.png";
		//
		inputPdfFile = new File("C:\\projetCrop\\pdfTestNotPropre\\VF - 300 dpi n&b.pdf");
		output = "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\VF - 300 dpi n&b-Crop.png";
		avantCrop = "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\VF - 300 dpi n&b-AvantCrop.png";

		BufferedImage image = getBufferedImageFromPdf(inputPdfFile);
		if (image != null) {

			// img avant crop
			getImageFileFromBufferedImage(image, avantCrop);

			CropWhitespace cropper = new CropWhitespace();
			image = cropper.crop(image);
			getImageFileFromBufferedImage(image, output);
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