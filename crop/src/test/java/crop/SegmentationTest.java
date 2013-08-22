package crop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public class SegmentationTest {

	private static final String DIR_OUTPUT_PNG_FILE = "C:\\projetCrop\\pdfTestNotPropre\\pngOutput\\";

	public static void main(String[] args) {
		// String fileName =
		// "RV_1_7fdbf204-e8b5-47ff-a256-3e829af0815b-Crop.png"; // ok

		// String fileName =
		// "RV_1_034baf66-46ce-494d-998f-767bc9930dc3_RV-Crop.png"; // ok

		// String fileName =
		// "RV_1_528f9275-bc72-468d-a849-efe4c2d59459-Crop.png"; // ok

		// String fileName =
		// "RV_1_72049fd1-88a3-426e-91ed-3cdeb115c0b4-Crop.png"; // ok

		// String fileName =
		// "RV_1_65363283-c827-4969-8b25-0a29fea43a78-Crop.png"; // ok

		// String fileName =
		// "RV_1_76411b98-ed93-4da1-a07b-5cd82218c2aa-Crop.png"; //ok

		// String fileName =
		// "RV_1_aa87cb24-544b-4f8c-916e-207aa1b952a0-Crop.png"; // ok

		// String fileName =
		// "RV_1_b02d0a30-5e30-4d97-ad3f-37cd32634fee-Crop.png"; // ok

		// String fileName =
		// "RV_1_ba5c1456-1f44-4f31-babe-3fa98b817c02-Crop.png"; // ok

		String fileName = "RV_1_88a9b9e3-9914-4f9f-a50c-f35aaa700dbc-Crop.png"; // bizare
		// :
		// avant
		// crop
		// img
		// retournee
		// 90°
		// ->
		// yGap

		// String fileName =
		// "RV_1_cc24a7cd-7e36-4180-8a34-2ff7e9f2ee80-Crop.png"; // bizare
		// 90°
		// avant
		// crop

		// String fileName =
		// "RV_1_2e7d3bf8-0ca4-44ac-8b07-f0391d54d12d-Crop.png"; // ok

		// String fileName =
		// "RV_1_3bd015fd-c47c-46dc-b9aa-f1a5526220b6-Crop.png"; // rgb
		// 249

		// String fileName =
		// "RV_1_a4bc2251-8e9a-4fb7-ae47-8fe4e97d56c3-Crop.png"; // ok

		// String fileName =
		// "RV_1_db824513-4dc7-42eb-9fa2-f2eddb425ad1-Crop.png"; // ok
		// vertical

		String fileNameCourt = StringUtils.substringBefore(fileName, ".png");
		File file = new File(DIR_OUTPUT_PNG_FILE + fileName);

		try {
			BufferedImage image = ImageIO.read(file);
			Segmentation segmentation = new Segmentation(image);
			segmentation.go();
			System.out.println("nb sous images : " + segmentation.getListeSousImages().size());

			if (segmentation.getListeSousImages().size() > 1) {
				// besoin de crop pour les nouvelles sous images
				int counter = 0;
				for (BufferedImage img : segmentation.getListeSousImages()) {
					counter++;
					File tmpFile = new File(DIR_OUTPUT_PNG_FILE + fileNameCourt + "-" + counter + ".png");
					ImageIO.write(img, "png", tmpFile);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
