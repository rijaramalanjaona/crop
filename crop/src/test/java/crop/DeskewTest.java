package crop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DeskewTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Deskew dsk = new Deskew();
		File inputFile = new File("C:\\projetCrop\\outputScaledPngFile.png");

		BufferedImage image;

		try {
			image = ImageIO.read(inputFile);
			double angle = dsk.doIt(image);
			System.out.println("angle : " + angle);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
