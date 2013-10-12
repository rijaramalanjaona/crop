package crop;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Permits scanning all the pixels of an {@link BufferedImage image} searching
 * for the first row/column and the last row/column (excluding bad edges) which
 * exceeds {@link #avgGrayTolerance} (passed into the constructor). The image is
 * then returned cropped after checking for and removing any bad edges.
 * <p>
 * <b>Note:</b> This class is not thread-safe. The same instance should not be
 * shared between mutiple threads.
 * 
 * @see #CropWhitespace()
 * @see #crop(BufferedImage)
 */
public class CropWhitespace {
	public static final int DEFAULT_GRAY_TOLERANCE = 250;
	public static final double DEFAULT_MARGIN_PERCENTAGE = 0.05;
	public static final int MIN_GRAY = 0;
	public static final int MAX_GRAY = 255;
	public static final double MIN_PERC = 0.0;
	public static final double MAX_PERC = 0.49;

	private static final int POURCENTAGE_DETECTION_NOISE = 7;
	private static final int POURCENTAGE_AUTOUR_BLANC = 80;

	/** The value of gray considered as whitespace or noise. */
	private final int avgGrayTolerance;

	/** The image to crop. */
	private BufferedImage image;

	/** The height of the {@link #image}. */
	private int height;
	/** The width of the {@link #image}. */
	private int width;

	/**
	 * Instead of dividing each average to compare with the
	 * {@link #avgGrayTolerance}, we multiply the <code>avgGrayTolerance</code>
	 * by the {@link #width} of the image once.
	 */
	private int grayToleranceWidth;

	/**
	 * Instead of dividing each average to compare with the
	 * {@link #avgGrayTolerance}, we multiply the <code>avgGrayTolerance</code>
	 * by the {@link #height} of the image once.
	 */
	private int grayToleranceHeight;

	/** The gray totals for each row of the image. */
	private int[] xGrayTotals;
	/** The gray totals for each column of the image. */
	private int[] yGrayTotals;

	/** The top-most non-gray row, excluding any bad edges. */
	private int xTop;
	/** The left-most non-gray column, excluding any bad edges. */
	private int yLeft;
	/** The bottom-most non-gray row, excluding any bad edges. */
	private int xBottom;
	/** The right-most non-gray column, excluding any bad edges. */
	private int yRight;

	/** The top-most non-gray row of a bad edge. */
	private int marginTop;
	/** The left-most non-gray column of a bad edge. */
	private int marginLeft;
	/** The bottom-most non-gray row of a bad edge. */
	private int marginBottom;
	/** The right-most non-gray column of a bad edge. */
	private int marginRight;

	/**
	 * An instance with a default average {@link #DEFAULT_GRAY_TOLERANCE gray
	 * tolerance} and {@link #DEFAULT_MARGIN_PERCENTAGE default margins} for bad
	 * edges.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>avgGrayTolerance</code> or
	 *             <code>percentOfHeight</code> are outside permitted values
	 * 
	 * @see #MIN_GRAY
	 * @see #MAX_GRAY
	 * @see #MIN_PERC
	 * @see #MAX_PERC
	 * @see #CropWhitespace(int)
	 * @see #CropWhitespace(double)
	 * @see #CropWhitespace(int, double)
	 * @see GrayColor#getGrayscale()
	 */
	public CropWhitespace() throws IllegalArgumentException {
		this(DEFAULT_GRAY_TOLERANCE, DEFAULT_MARGIN_PERCENTAGE);
	}

	/**
	 * An instance with a specific average gray tolerance and
	 * {@link #DEFAULT_MARGIN_PERCENTAGE default margins} for bad edges.
	 * 
	 * @param avgGrayTolerance
	 *            a value between 0 & 255 (inclusive)
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>avgGrayTolerance</code> or
	 *             <code>percentOfHeight</code> are outside permitted values
	 * 
	 * @see #MIN_GRAY
	 * @see #MAX_GRAY
	 * @see #MIN_PERC
	 * @see #MAX_PERC
	 * @see #CropWhitespace()
	 * @see #CropWhitespace(double)
	 * @see #CropWhitespace(int, double)
	 * @see GrayColor#getGrayscale()
	 */
	public CropWhitespace(int avgGrayTolerance) throws IllegalArgumentException {
		this(avgGrayTolerance, DEFAULT_MARGIN_PERCENTAGE);
	}

	/**
	 * An instance with a specific margin for bad edges and default average
	 * {@link #DEFAULT_GRAY_TOLERANCE gray tolerance}.
	 * 
	 * @param percentOfHeight
	 *            a percentage (between 0.0 and 0.49) of the height of the
	 *            document in which to search for (and exclude) bad edges
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>avgGrayTolerance</code> or
	 *             <code>percentOfHeight</code> are outside permitted values
	 * 
	 * @see #MIN_GRAY
	 * @see #MAX_GRAY
	 * @see #MIN_PERC
	 * @see #MAX_PERC
	 * @see #CropWhitespace()
	 * @see #CropWhitespace(int)
	 * @see #CropWhitespace(int, double)
	 * @see GrayColor#getGrayscale()
	 */
	public CropWhitespace(double percentOfHeight) throws IllegalArgumentException {
		this(DEFAULT_GRAY_TOLERANCE, percentOfHeight);
	}

	/**
	 * An instance with a specific average gray tolerance and margin for bad
	 * edges.
	 * 
	 * @param avgGrayTolerance
	 *            a value between 0 & 255 (inclusive)
	 * @param marginPercent
	 *            a percentage (between 0.0 and 0.49) of the document in which
	 *            to search for (and exclude) bad edges. The number of pixels
	 *            for the margin is calculated from the largest dimension
	 *            (height or width).
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>avgGrayTolerance</code> or
	 *             <code>percentOfHeight</code> are outside permitted values
	 * 
	 * @see #MIN_GRAY
	 * @see #MAX_GRAY
	 * @see #MIN_PERC
	 * @see #MAX_PERC
	 * @see #CropWhitespace()
	 * @see #CropWhitespace(int)
	 * @see #CropWhitespace(double)
	 * @see GrayColor#getGrayscale()
	 */
	public CropWhitespace(int avgGrayTolerance, double marginPercent) throws IllegalArgumentException {
		if (avgGrayTolerance < MIN_GRAY || avgGrayTolerance > MAX_GRAY) {
			throw new IllegalArgumentException(String.format(
					"The average gray tolerance must be between %d and %d inclusive", MIN_GRAY, MAX_GRAY));
		}

		if (marginPercent < MIN_PERC || marginPercent >= MAX_PERC) {
			throw new IllegalArgumentException(String.format(
					"The percentage of height must be between %d and %d inclusive", MIN_PERC, MAX_PERC));
		}

		this.avgGrayTolerance = avgGrayTolerance;
	}

	/**
	 * Scans all the pixels of the image searching for the first row/column and
	 * the last row/column (excluding bad edges) which exceeds
	 * {@link #avgGrayTolerance} (passed into the constructor). The image is
	 * then returned cropped after checking for and removing any bad edges.
	 * 
	 * @param image
	 *            an image which can be null
	 * @return the cropped image or the original image if it's considered no
	 *         rectangle can be found or <code>null</code> if <code>image</code>
	 *         is <code>null</code>
	 */
	public BufferedImage crop(BufferedImage image) {
		if (image == null) {
			return null;
		}

		this.image = image;

		reinitVariables();
		createGrayTotals();
		trackBadEdges();
		findRectangleCoordinates();

		return cropImage();
	}

	/**
	 * Saves passing values around to all the different methods while
	 * reinitialising them from previous calls to {@link #cropImage()}.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void reinitVariables() {
		this.height = image.getHeight();
		this.width = image.getWidth();

		this.grayToleranceWidth = this.avgGrayTolerance * this.width;
		this.grayToleranceHeight = this.avgGrayTolerance * this.height;

		this.xGrayTotals = new int[height];
		this.yGrayTotals = new int[width];

		this.xTop = Integer.MAX_VALUE;
		this.yLeft = Integer.MAX_VALUE;

		this.xBottom = 0;
		this.yRight = 0;
	}

	/**
	 * Scans each row and column of pixels totaling up the levels of gray for
	 * each pixel.
	 * 
	 * @see GrayColor#getGrayscale()
	 */
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

	/**
	 * Prepare to exclude any bad (dark) edges.
	 */
	public void trackBadEdges() {
		marginLeft = 0;
		marginRight = width;
		marginTop = 0;
		marginBottom = height;
	}

	/**
	 * Working from within the margins found previously (hopefully inside any
	 * bad margins) find the extremeties where the pixels aren't whitespace (the
	 * part of the image we want to keep).
	 */
	public void findRectangleCoordinates() {

		xTop = 0;
		xBottom = height;
		yLeft = 0;
		yRight = width;

		checkXTop();
		if (xTop == height + 1) {
			xTop = height;
		}

		checkXBottom();
		if (xBottom == -1) {
			xBottom = height;
		}

		checkYLeft();
		if (yLeft == width + 1) {
			yLeft = width;
		}

		checkYRight();
		if (yRight == -1) {
			yRight = width;
		}

	}

	public void checkXTop() {
		int nbDetectionNoiseHeight = height * POURCENTAGE_DETECTION_NOISE / 100;

		boolean limiteXTopAtteinte = false;
		int nbRowBlancFromXTop = 0;
		double pourcentageBlancFromXTop;
		xTop = height + 1;

		for (int i = marginTop; i < marginBottom; i++) {
			if (xGrayTotals[i] < grayToleranceWidth) {
				xTop = Math.min(xTop, i); // top-most non-gray row
			}
		}

		// check si xTop est bien le xTop ou si c'est des noises et blancs
		// autour
		for (int k = xTop + 1; k <= xTop + nbDetectionNoiseHeight; k++) {
			if (k < xGrayTotals.length) {
				if (xGrayTotals[k] >= grayToleranceWidth) {
					nbRowBlancFromXTop++;
				}
			} else {
				limiteXTopAtteinte = true;
				break;
			}
		}

		if (!limiteXTopAtteinte && marginTop <= height) {
			pourcentageBlancFromXTop = (double) nbRowBlancFromXTop * 100 / nbDetectionNoiseHeight;

			if (pourcentageBlancFromXTop > POURCENTAGE_AUTOUR_BLANC) {
				marginTop = marginTop + nbDetectionNoiseHeight;
				checkXTop();
			}
		}
	}

	public void checkXBottom() {
		int nbDetectionNoiseHeight = height * POURCENTAGE_DETECTION_NOISE / 100;

		boolean limiteXBottomAtteinte = false;
		int nbRowBlancFromXBottom = 0;
		double pourcentageBlancFromXBottom;
		xBottom = -1;

		for (int i = marginTop; i < marginBottom; i++) {
			if (xGrayTotals[i] < grayToleranceWidth) {
				xBottom = Math.max(xBottom, i); // bottom-most non-gray row
			}
		}

		// check si xBottom est bien le xBottom ou si c'est des noises et blancs
		// autour
		for (int k = xBottom - 1; k >= xBottom - nbDetectionNoiseHeight; k--) {
			if (k > 0) {
				if (xGrayTotals[k] >= grayToleranceWidth) {
					nbRowBlancFromXBottom++;
				}
			} else {
				limiteXBottomAtteinte = true;
				break;
			}
		}

		if (!limiteXBottomAtteinte && marginBottom >= 0) {
			pourcentageBlancFromXBottom = (double) nbRowBlancFromXBottom * 100 / nbDetectionNoiseHeight;

			if (pourcentageBlancFromXBottom > POURCENTAGE_AUTOUR_BLANC) {
				marginBottom = marginBottom - nbDetectionNoiseHeight;
				checkXBottom();

			}
		}

	}

	public void checkYLeft() {
		int nbDetectionNoiseWidth = width * POURCENTAGE_DETECTION_NOISE / 100;

		boolean limiteYLeftAtteinte = false;
		int nbRowBlancFromYLeft = 0;
		double pourcentageBlancFromYLeft;
		yLeft = width + 1;

		for (int i = marginLeft; i < marginRight; i++) {
			if (yGrayTotals[i] < grayToleranceHeight) {
				yLeft = Math.min(yLeft, i); // left-most non-gray column
			}
		}

		// check si yLeft est bien le yLeft ou si c'est des noises et blancs
		// autour
		for (int k = yLeft + 1; k <= yLeft + nbDetectionNoiseWidth; k++) {
			if (k < yGrayTotals.length) {
				if (yGrayTotals[k] >= grayToleranceHeight) {
					nbRowBlancFromYLeft++;
				}
			} else {
				limiteYLeftAtteinte = true;
				break;
			}
		}

		if (!limiteYLeftAtteinte && marginLeft <= width) {
			pourcentageBlancFromYLeft = (double) nbRowBlancFromYLeft * 100 / nbDetectionNoiseWidth;

			if (pourcentageBlancFromYLeft > POURCENTAGE_AUTOUR_BLANC) {
				marginLeft = marginLeft + nbDetectionNoiseWidth;
				checkYLeft();
			}
		}
	}

	public void checkYRight() {
		int nbDetectionNoiseWidth = width * POURCENTAGE_DETECTION_NOISE / 100;

		boolean limiteYRightAtteinte = false;
		int nbRowBlancFromYRight = 0;
		double pourcentageBlancFromYRight;
		yRight = -1;

		for (int i = marginLeft; i < marginRight; i++) {
			if (yGrayTotals[i] < grayToleranceHeight) {
				yRight = Math.max(yRight, i); // right-most non-gray column
			}
		}

		// check si yRight est bien le yRight ou si c'est des noises et blancs
		// autour
		for (int k = yRight - 1; k >= yRight - nbDetectionNoiseWidth; k--) {
			if (k > 0) {
				if (yGrayTotals[k] >= grayToleranceHeight) {
					nbRowBlancFromYRight++;
				}
			} else {
				limiteYRightAtteinte = true;
				break;
			}
		}

		if (!limiteYRightAtteinte && marginRight >= 0) {
			pourcentageBlancFromYRight = (double) nbRowBlancFromYRight * 100 / nbDetectionNoiseWidth;

			if (pourcentageBlancFromYRight > POURCENTAGE_AUTOUR_BLANC) {
				marginRight = marginRight - nbDetectionNoiseWidth;
				checkYRight();
			}
		}
	}

	/**
	 * Once all the relevant values have been set, we're ready to crop the
	 * image, returning a new {@link BufferedImage} in the process.
	 * 
	 * @return a new image or the same image passed in if no boundaries were
	 *         found
	 */
	public BufferedImage cropImage() {
		if (xTop == Integer.MAX_VALUE || yLeft == Integer.MAX_VALUE) {
			System.out.println("Unable to crop image... use orginial");
			return image;

		} else {
			int newWidth = yRight - yLeft;
			int newHeight = xBottom - xTop;
			System.out.format("cropping extraneous whitespace : w=%d, h=%d --> w=%d, h=%d%n", width, height, newWidth,
					newHeight);

			WritableRaster rr = image.getRaster();
			rr = rr.createWritableChild(yLeft, xTop, newWidth, newHeight, 0, 0, null);

			ColorModel cm = image.getColorModel();
			return new BufferedImage(cm, rr, cm.isAlphaPremultiplied(), null);
		}
	}

	/**
	 * Crop the image directly, no recognition is done. Only really useful for
	 * testing for ex. :
	 * 
	 * <pre>
	 * <code>
	 * CropWhitespace cropper = new CropWhitespace();
	 * image = cropper.cropPure(image, 10, 10, 10, 10);
	 * image = cropper.crop(image);
	 * </code>
	 * </pre>
	 * 
	 * @param image
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 * @return the cropped image or <code>null</code> if image is
	 *         <code>null</code>
	 */
	public BufferedImage cropPure(BufferedImage image, int top, int right, int bottom, int left)
			throws IllegalArgumentException {
		if (image == null) {
			return null;
		}
		checkArgs(image.getWidth(), image.getHeight(), top, right, bottom, left);

		int newWidth = image.getWidth() - right;
		int newHight = image.getHeight() - bottom;

		WritableRaster rr = image.getRaster();
		rr = rr.createWritableChild(top, left, newWidth, newHight, 0, 0, null);

		ColorModel cm = image.getColorModel();
		return new BufferedImage(cm, rr, cm.isAlphaPremultiplied(), null);
	}

	/**
	 * Checks to see if the left/right top/bottom is inside the specified
	 * width/height and possibly throws an exception.
	 * 
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 * @throws IllegalArgumentException
	 *             for a left/right top/bottom outside the width/height of the
	 *             image.
	 */
	public void checkArgs(int width, int height, int top, int right, int bottom, int left)
			throws IllegalArgumentException {
		if (top < 0 || right < 0 || bottom < 0 || left < 0) {
			throw new IllegalArgumentException( //
					String.format("Margins can't be less than 0 : %d, %d, %d, %d", //
							top, right, bottom, left));
		}

		if (top + bottom >= height) {
			throw new IllegalArgumentException(
			//
					String.format("Top and bottom margins can't be >= the height (%d) : %d + %d = %d", //
							height, top, bottom, top + bottom));
		}

		if (left + right >= width) {
			throw new IllegalArgumentException(
			//
					String.format("Left and right margins can't be >= the width (%d) : %d + %d = %d", //
							width, left, right, left + right));
		}
	}

}
