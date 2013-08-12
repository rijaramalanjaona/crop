package crop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javaxt.io.Image;

public class CornersCropScaledImageTest {

	public static void main(String[] args) {
		System.out.println("------------------ CornersCropScaledImageTest -----------------");

		// File inputFile = new File("C:\\projetCrop\\outputScaledPngFile.png");
		//
		// BufferedImage image;
		// try {
		// image = ImageIO.read(inputFile);
		// Corners corners = new Corners(image);
		//
		// System.out.println("width : " + image.getWidth() + " height : " +
		// image.getHeight());
		// System.out.println("Affichage corners");
		// // System.out.println("Found top corner : " + corners.getTop());
		// // System.out.println("Found right corner : " + corners.getRight());
		// // System.out.println("Found bottom corner : " +
		// // corners.getBottom());
		// // System.out.println("Found left corner : " + corners.getLeft());
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// test javaxt
		File inputFile = new File("C:\\projetCrop\\outputPngFile.png");
		Image image = new Image(inputFile);
		image.trim();
		image.saveAs("C:\\projetCrop\\copyTrim2.png");
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