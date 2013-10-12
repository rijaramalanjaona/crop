package crop;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe utilisee pour separer horizontalement ou verticalement 2 pieces (recto
 * et verso) dans une meme page; se base sur la detection de gap horizontal ou
 * vertical.
 * 
 */
public class Segmentation {
	private static final int DEFAULT_GRAY_TOLERANCE = 250;
	private static final int POURCENTAGE_DETECTION_GAP = 7;
	private static final int POURCENTAGE_AUTOUR_BLANC = 80;

	private BufferedImage image;
	private int height;
	private int width;

	private int[] xGrayTotals;
	private int[] yGrayTotals;

	private int avgGrayTolerance;
	private int grayToleranceWidth;
	private int grayToleranceHeight;

	private int xGap = 0;
	private int yGap = 0;
	private ArrayList<Integer> listeBlancsX = new ArrayList<Integer>();
	private ArrayList<Integer> listeBlancsY = new ArrayList<Integer>();

	private ArrayList<BufferedImage> listeSousImages = new ArrayList<BufferedImage>();

	public Segmentation(BufferedImage image) {
		this.image = image;
		this.height = image.getHeight();
		this.width = image.getWidth();

		this.xGrayTotals = new int[height];
		this.yGrayTotals = new int[width];

		this.avgGrayTolerance = DEFAULT_GRAY_TOLERANCE;
		this.grayToleranceWidth = this.avgGrayTolerance * this.width;
		this.grayToleranceHeight = this.avgGrayTolerance * this.height;
	}

	public void go() throws IOException {
		createGrayTotals();
		fillListeBlancsX();

		if (!listeBlancsX.isEmpty()) {
			int firstXBlanc = listeBlancsX.get(0);
			findHorizontalGap(firstXBlanc);
		}

		if (xGap != 0) {
			System.out.println(">>> on a un xGap : " + xGap);
			segmentationHorizontal();
		} else {
			System.out.println(">>> on n'a pas de xGap, on va chercher un yGap");
			fillListeBlancsY();
			if (!listeBlancsY.isEmpty()) {
				int firstYBlanc = listeBlancsY.get(0);
				findVerticalGap(firstYBlanc);
			}
			if (yGap != 0) {
				System.out.println(">>> on a un yGap : " + yGap);
				segmentationVertical();
			} else {
				System.out.println(">>> on n'a pas yGap");
			}
		}

		if (listeSousImages.isEmpty()) {
			listeSousImages.add(image);
		}
	}

	public void createGrayTotals() {
		int maxGray = 0;
		int minGray = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int argb = image.getRGB(x, y);
				int gray = new GrayColor(argb).getGrayscale();
				maxGray = Math.max(gray, maxGray);
				minGray = Math.min(gray, minGray);
				xGrayTotals[y] += gray;
				yGrayTotals[x] += gray;
			}
		}
	}

	public void fillListeBlancsX() {
		for (int i = 0; i < xGrayTotals.length; i++) {
			if (xGrayTotals[i] >= grayToleranceWidth) {
				listeBlancsX.add(i);
			}
		}
	}

	public void fillListeBlancsY() {
		for (int i = 0; i < yGrayTotals.length; i++) {
			if (yGrayTotals[i] >= grayToleranceHeight) {
				listeBlancsY.add(i);
			}
		}
	}

	public void findHorizontalGap(int xBlanc) {
		if (!listeBlancsX.isEmpty()) {
			int nbDetectionGapHeight = height * POURCENTAGE_DETECTION_GAP / 100;
			int nbRowBlancFromXBlanc = 0;
			double pourcentageBlancFromXBlanc;
			boolean limiteXBlancAtteinte = false;

			for (int k = xBlanc + 1; k <= xBlanc + nbDetectionGapHeight; k++) {
				if (k < xGrayTotals.length) {
					if (xGrayTotals[k] >= grayToleranceWidth) {
						nbRowBlancFromXBlanc++;
					}
				} else {
					limiteXBlancAtteinte = true;
					break;
				}
			}

			if (!limiteXBlancAtteinte) {
				pourcentageBlancFromXBlanc = (double) nbRowBlancFromXBlanc * 100 / nbDetectionGapHeight;

				if (pourcentageBlancFromXBlanc > POURCENTAGE_AUTOUR_BLANC) {
					xGap = xBlanc + (nbDetectionGapHeight / 2);

				} else {
					int minNewXBlanc = xBlanc + nbDetectionGapHeight;
					int newXBlanc = minNewXBlanc;
					for (int x : listeBlancsX) {
						if (x >= minNewXBlanc) {
							newXBlanc = x;
							break;
						}
					}
					if (newXBlanc < xGrayTotals.length) {
						findHorizontalGap(newXBlanc);
					}
				}
			}

		}
	}

	public void findVerticalGap(int yBlanc) {
		if (!listeBlancsY.isEmpty()) {
			int nbDetectionGapWidth = width * POURCENTAGE_DETECTION_GAP / 100;
			int nbColBlancFromYBlanc = 0;
			double pourcentageBlancFromYBlanc;
			boolean limiteYBlancAtteinte = false;

			for (int k = yBlanc + 1; k <= yBlanc + nbDetectionGapWidth; k++) {
				if (k < yGrayTotals.length) {
					if (yGrayTotals[k] >= grayToleranceHeight) {
						nbColBlancFromYBlanc++;
					}
				} else {
					limiteYBlancAtteinte = true;
					break;
				}
			}

			if (!limiteYBlancAtteinte) {
				pourcentageBlancFromYBlanc = (double) nbColBlancFromYBlanc * 100 / nbDetectionGapWidth;

				if (pourcentageBlancFromYBlanc > POURCENTAGE_AUTOUR_BLANC) {
					yGap = yBlanc + (nbDetectionGapWidth / 2);

				} else {
					int minNewYBlanc = yBlanc + nbDetectionGapWidth;
					int newYBlanc = minNewYBlanc;
					for (int y : listeBlancsY) {
						if (y >= minNewYBlanc) {
							newYBlanc = y;
							break;
						}
					}
					if (newYBlanc < yGrayTotals.length) {
						findVerticalGap(newYBlanc);
					}
				}
			}

		}
	}

	public void segmentationHorizontal() throws IOException {
		int newHeight1 = xGap;
		int newHeight2 = height - xGap;

		ColorModel cm = image.getColorModel();

		WritableRaster rr1 = image.getRaster();
		rr1 = rr1.createWritableChild(0, 0, width, newHeight1, 0, 0, null);
		BufferedImage image1 = new BufferedImage(cm, rr1, cm.isAlphaPremultiplied(), null);
		listeSousImages.add(image1);

		WritableRaster rr2 = image.getRaster();
		rr2 = rr2.createWritableChild(0, xGap, width, newHeight2, 0, 0, null);
		BufferedImage image2 = new BufferedImage(cm, rr2, cm.isAlphaPremultiplied(), null);
		listeSousImages.add(image2);
	}

	public void segmentationVertical() throws IOException {
		int newWidth1 = yGap;
		int newWidth2 = width - yGap;

		ColorModel cm = image.getColorModel();

		WritableRaster rr1 = image.getRaster();
		rr1 = rr1.createWritableChild(0, 0, newWidth1, height, 0, 0, null);
		BufferedImage image1 = new BufferedImage(cm, rr1, cm.isAlphaPremultiplied(), null);
		listeSousImages.add(image1);

		WritableRaster rr2 = image.getRaster();
		rr2 = rr2.createWritableChild(yGap, 0, newWidth2, height, 0, 0, null);
		BufferedImage image2 = new BufferedImage(cm, rr2, cm.isAlphaPremultiplied(), null);
		listeSousImages.add(image2);
	}

	public ArrayList<BufferedImage> getListeSousImages() {
		return listeSousImages;
	}

	public void setListeSousImages(ArrayList<BufferedImage> listeSousImages) {
		this.listeSousImages = listeSousImages;
	}

}
