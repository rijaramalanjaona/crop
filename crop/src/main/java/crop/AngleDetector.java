package crop;

import java.awt.image.BufferedImage;

import crop.Corners.BestCorner;
import crop.Corners.Coord;

/**
 * Detects an angle of rotation.
 * <p>
 * <b>Note:</b> This class is not thread-safe. The same instance should not be
 * shared between mutiple threads.
 */
public class AngleDetector {
    public static final int DEFAULT_GRAY_TOLERANCE = 250;
    public static final int DEFAULT_PIXEL_MARGIN = 3;

    private int avgGrayTolerance;
    /** So we don't get thrown off by a single bad pixel. */
    private int pixelMargin;

    private BufferedImage image;
    private int width;
    private int height;

    public AngleDetector() {
	this(DEFAULT_GRAY_TOLERANCE, DEFAULT_PIXEL_MARGIN);
    }

    public AngleDetector(int avgGrayTolerance, int pixelMargin) {
	this.avgGrayTolerance = avgGrayTolerance;
	this.pixelMargin = pixelMargin;
    }

    public double getRotation(BufferedImage image) {
	if (image == null) {
	    return 0;
	}

	this.image = image;
	Corners corners = new Corners(image);
	width = image.getWidth();
	height = image.getHeight();

	System.out.println("Found top corner : " + corners.getTop());
	System.out.println("Found right corner : " + corners.getRight());
	System.out.println("Found bottom corner : " + corners.getBottom());
	System.out.println("Found left corner : " + corners.getLeft());

	BestCorner bestCorner = corners.getBestCorner();
	System.out.println("Best corner is : " + bestCorner);

	if (bestCorner.isTop()) {
	    Coord top = corners.getTop();
	    if (top.getX() <= top.getX() - width) { // close to the left
		int distance = width - top.getX();
		int third = distance / 3;
		int x1 = top.getX() + third;
		int x2 = top.getX() + third + third;
		// TODO : check for long side or short side
		// TODO : possibly add 90 degrees
		return calcAngleFromTop(x1, x2);

	    } else { // close to the right
		int third = top.getX() / 3;
		int x1 = third;
		int x2 = third + third;
		// TODO : check for long side or short side
		// TODO : possibly add 90 degrees
		// negate the angle to rotate the other way
		return -calcAngleFromTop(x1, x2);

	    }
	} else if (bestCorner.isRight()) {
	    // TODO : check for long side or short side
	    // TODO : possibly add 90 degrees
	    return 0;

	} else if (bestCorner.isBottom()) {
	    // TODO : check for long side or short side
	    // TODO : possibly add 90 degrees
	    return 0;

	} else {
	    assert bestCorner.isLeft();
	    // TODO : check for long side or short side
	    // TODO : possibly add 90 degrees
	    return 0;

	}
    }

    private double calcAngleFromTop(int x1, int x2) {
	int first = -1;
	for (int y = 0; y < height; y++) {
	    int grayTotal = 0;
	    for (int i = x1 - pixelMargin; i < x1 + pixelMargin; i++) {
		try {
		    grayTotal += new GrayColor(image.getRGB(i, y))
			    .getGrayscale();
		} catch (RuntimeException e) {
		    System.out.println("x1=" + x1 + "  i=" + i);
		    throw e;
		}
	    }
	    if (grayTotal < avgGrayTolerance * pixelMargin * 2) {
		first = y;
		break;
	    }
	}

	int second = -1;
	for (int y = 0; y < height; y++) {
	    int grayTotal = 0;
	    for (int i = x2 - pixelMargin; i < x2 + pixelMargin; i++) {
		grayTotal += new GrayColor(image.getRGB(i, y)).getGrayscale();
	    }
	    if (grayTotal < avgGrayTolerance * pixelMargin * 2) {
		second = y;
		break;
	    }
	}

	double opposite = second - first;
	double adjacent = x2 - x1;
	return Math.toDegrees(Math.atan(opposite / adjacent));
    }

}
