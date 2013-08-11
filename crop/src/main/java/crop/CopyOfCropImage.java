package crop;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;


public class CopyOfCropImage {
	private static final int WHITE = -1;
	private static final String SQUARE_FILENAME = "Square.png";

	private static BufferedImage avg(BufferedImage original) {
		int alpha, red, green, blue;
		int newPixel;

		BufferedImage avg_gray = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
		int[] avgLUT = new int[766];
		for (int i = 0; i < avgLUT.length; i++) {
			avgLUT[i] = (i / 3);
		}

		for (int y = 0; y < original.getHeight(); y++) {
			for (int x = 0; x < original.getWidth(); x++) {
				// Get pixels by R, G, B
				alpha = new Color(original.getRGB(x, y)).getAlpha();
				red = new Color(original.getRGB(x, y)).getRed();
				green = new Color(original.getRGB(x, y)).getGreen();
				blue = new Color(original.getRGB(x, y)).getBlue();

				newPixel = red + green + blue;
				newPixel = avgLUT[newPixel];
				// Return back to original format
				newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);

				// Write pixels into image
				avg_gray.setRGB(x, y, newPixel);

				System.out.print(StringUtils.leftPad("" + newPixel, 9, ' '));
				System.out.print(", ");
			}
			System.out.println();
		}

		return avg_gray;
	}

	private static BufferedImage trimNoise(BufferedImage image) {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int argb = image.getRGB(x, y);

				if (argb != WHITE) {
					Color c = new Color(argb);
					int alpha = c.getAlpha();
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					int average = red + green + blue;
					int gray = colorToRGB(alpha, average, average, average);
					image.setRGB(x, y, gray);
					argb = gray;
				}
				System.out.print(StringUtils.leftPad("" + argb, 9, ' '));
				System.out.print(", ");
			}
			System.out.println();
		}

		WritableRaster rr = image.getRaster();
		ColorModel cm = image.getColorModel();
		rr = rr.createWritableChild(0, 0, image.getWidth(), image.getHeight(), 0, 0, null);
		return new BufferedImage(cm, rr, cm.isAlphaPremultiplied(), null);
	}

	/**
	 * Convert Alpha, R, G, B to argb.
	 * 
	 * @param alpha
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	private static int colorToRGB(int alpha, int red, int green, int blue) {
		int newPixel = 0;

		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;

		return newPixel;
	}

	private static final int GRAY_TOLERANCE = 235 * 3;

	private static BufferedImage trimWhite(BufferedImage image) {
		int x1 = Integer.MAX_VALUE;
		int y1 = Integer.MAX_VALUE;
		int x2 = 0;
		int y2 = 0;
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int argb = image.getRGB(x, y);
				int gray = -1;
				if (argb != WHITE) {
					Color color = new Color(argb);
					int red = color.getRed();
					int green = color.getGreen();
					int blue = color.getBlue();

					gray = red + green + blue;
					if (gray < GRAY_TOLERANCE) {
						x1 = Math.min(x1, x);
						y1 = Math.min(y1, y);
						x2 = Math.max(x2, x);
						y2 = Math.max(y2, y);
					}
				}
				System.out.print(StringUtils.leftPad("" + gray, 9, ' '));
				System.out.print(", ");
			}
			System.out.println();
		}

		int width = x2 - x1;
		int height = y2 - y1;
		WritableRaster rr = image.getRaster();
		ColorModel cm = image.getColorModel();
		rr = rr.createWritableChild(x1, y1, width, height, 0, 0, null);
		return new BufferedImage(cm, rr, cm.isAlphaPremultiplied(), null);
	}

	public static BufferedImage convertToGray(BufferedImage source) {
		BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		return op.filter(source, null);
	}

	private void go() throws IOException, URISyntaxException {
		URL url = getClass().getResource(SQUARE_FILENAME);
		URI uri = new URI(url.toString());
		File squareFile = new File(uri);
		InputStream in = new FileInputStream(squareFile);
		BufferedImage bi = ImageIO.read(in);
		bi = trimWhite(bi);
		// bi = trimNoise(bi);
		// bi = convertToGray(bi);
		// bi = avg(bi);
		File output = new File(squareFile.getParent().replace("\\bin\\", "\\src\\") + "/Trimmed.png");
		ImageIO.write(bi, "png", output);
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		CopyOfCropImage ci = new CopyOfCropImage();
		ci.go();
		System.out.println("fini");
	}

}
