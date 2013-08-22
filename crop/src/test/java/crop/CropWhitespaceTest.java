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

		inputPdfFile = new File("C:\\projetCrop\\pdfTestPropre\\test2.pdf");
		output = "C:\\projetCrop\\pdfTestPropre\\pngOutput\\test2-Crop.jpg";
		avantCrop = "C:\\projetCrop\\pdfTestPropre\\pngOutput\\test2-AvantCrop.jpg";

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
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\VF - 300 dpi n&b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\VF - 300 dpi n&b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\VF - 300 dpi n&b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_034baf66-46ce-494d-998f-767bc9930dc3_RV.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_034baf66-46ce-494d-998f-767bc9930dc3_RV-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_034baf66-46ce-494d-998f-767bc9930dc3_RV-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_7fdbf204-e8b5-47ff-a256-3e829af0815b.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_7fdbf204-e8b5-47ff-a256-3e829af0815b-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_7fdbf204-e8b5-47ff-a256-3e829af0815b-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_528f9275-bc72-468d-a849-efe4c2d59459.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_528f9275-bc72-468d-a849-efe4c2d59459-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_528f9275-bc72-468d-a849-efe4c2d59459-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_72049fd1-88a3-426e-91ed-3cdeb115c0b4.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_72049fd1-88a3-426e-91ed-3cdeb115c0b4-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_72049fd1-88a3-426e-91ed-3cdeb115c0b4-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_65363283-c827-4969-8b25-0a29fea43a78.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_65363283-c827-4969-8b25-0a29fea43a78-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_65363283-c827-4969-8b25-0a29fea43a78-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_76411b98-ed93-4da1-a07b-5cd82218c2aa.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_76411b98-ed93-4da1-a07b-5cd82218c2aa-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_76411b98-ed93-4da1-a07b-5cd82218c2aa-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_aa87cb24-544b-4f8c-916e-207aa1b952a0.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_aa87cb24-544b-4f8c-916e-207aa1b952a0-Crop.png";
		// avantCrop =
		// //
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_aa87cb24-544b-4f8c-916e-207aa1b952a0-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_b02d0a30-5e30-4d97-ad3f-37cd32634fee.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_b02d0a30-5e30-4d97-ad3f-37cd32634fee-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_b02d0a30-5e30-4d97-ad3f-37cd32634fee-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_ba5c1456-1f44-4f31-babe-3fa98b817c02.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_ba5c1456-1f44-4f31-babe-3fa98b817c02-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_ba5c1456-1f44-4f31-babe-3fa98b817c02-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_88a9b9e3-9914-4f9f-a50c-f35aaa700dbc.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_88a9b9e3-9914-4f9f-a50c-f35aaa700dbc-Crop.png";
		// avantCrop =
		// //
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_88a9b9e3-9914-4f9f-a50c-f35aaa700dbc-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_bis_88a9b9e3-9914-4f9f-a50c-f35aaa700dbc.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_bis_88a9b9e3-9914-4f9f-a50c-f35aaa700dbc-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_bis_88a9b9e3-9914-4f9f-a50c-f35aaa700dbc-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_cc24a7cd-7e36-4180-8a34-2ff7e9f2ee80.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_cc24a7cd-7e36-4180-8a34-2ff7e9f2ee80-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_cc24a7cd-7e36-4180-8a34-2ff7e9f2ee80-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_2e7d3bf8-0ca4-44ac-8b07-f0391d54d12d.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_2e7d3bf8-0ca4-44ac-8b07-f0391d54d12d-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_2e7d3bf8-0ca4-44ac-8b07-f0391d54d12d-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_3bd015fd-c47c-46dc-b9aa-f1a5526220b6.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_3bd015fd-c47c-46dc-b9aa-f1a5526220b6-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_3bd015fd-c47c-46dc-b9aa-f1a5526220b6-AvantCrop.png";

		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_a4bc2251-8e9a-4fb7-ae47-8fe4e97d56c3.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_a4bc2251-8e9a-4fb7-ae47-8fe4e97d56c3-Crop.png";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_a4bc2251-8e9a-4fb7-ae47-8fe4e97d56c3-AvantCrop.png";
		//
		// inputPdfFile = new
		// File("C:\\projetCrop\\pdfTestNotPropre\\RV_1_db824513-4dc7-42eb-9fa2-f2eddb425ad1.pdf");
		// output =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_db824513-4dc7-42eb-9fa2-f2eddb425ad1-Crop.jpeg";
		// avantCrop =
		// "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\RV_1_db824513-4dc7-42eb-9fa2-f2eddb425ad1-AvantCrop.jpeg";

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
			ImageIO.write(bufferedImage, "jpg", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageFile;
	}

}