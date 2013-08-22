package crop;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RotationBiTest {

	public static BufferedImage rotateMyImage(BufferedImage img, double angle) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.rotate(Math.toRadians(angle), w, h);
		g.drawImage(img, null, 0, 0);
		return dimg;
	}

	public static BufferedImage rotate(BufferedImage img, double angle) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.rotate(Math.toRadians(angle), w / 2, h / 2);
		g.drawImage(img, null, 0, 0);
		return dimg;
	}

	public static BufferedImage replaceImage(Image img, double angleRotation) {
		double absA = Math.abs(angleRotation);
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		int dy = (int) (w * Math.sin(absA));
		int dx = (int) (w - w * Math.cos(absA));

		int hauteur = h + dy;
		int largeur = w - dx;
		int delta = (int) (h * Math.sin(absA));

		BufferedImage imageRotate = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = (Graphics2D) imageRotate.getGraphics();

		AffineTransform translation = new AffineTransform();
		if (angleRotation > 0) {
			translation.translate(0, hauteur - h);
			g2d.transform(translation);
			translation.setToIdentity();
			translation.translate(-delta, 0);
			g2d.transform(translation);
		} else {
			translation.translate(delta, 0);
			g2d.transform(translation);
		}

		AffineTransform rotation = new AffineTransform();
		rotation.rotate(-angleRotation);

		g2d.transform(rotation);
		g2d.drawImage(img, 0, 0, null);
		return imageRotate;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File file = new File("C:\\projetCrop\\outputScaledPng\\test2-page1-outputScaledPngFile.png");
		BufferedImage image = ImageIO.read(file);
		BufferedImage imgRot = replaceImage(image, 92.12523536794032);
		File tmpFile = new File("C:\\projetCrop\\outputScaledPng\\tmpxtRotation.png");
		ImageIO.write(imgRot, "png", tmpFile);

		// Image imgxt = new Image(image);
		// imgxt.rotate(86.47940419027391);
		//
		// BufferedImage bi = imgxt.getBufferedImage();
		// File tmpFile = new
		// File("C:\\projetCrop\\outputPng\\tmpxtRotation.png");
		// ImageIO.write(bi, "png", tmpFile);

	}

}
