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
	double angle;

	System.out.println("Found top corner : " + corners.getTop());
	System.out.println("Found right corner : " + corners.getRight());
	System.out.println("Found bottom corner : " + corners.getBottom());
	System.out.println("Found left corner : " + corners.getLeft());

	BestCorner bestCorner = corners.getBestCorner();
	System.out.println("Best corner is : " + bestCorner);

	if (bestCorner.isTop()) {
	    Coord top = corners.getTop();
	    if (top.getX() <= getHalf(width)) {
		System.out
			.println("calcul angle : top.getX() <= getHalf(width) ");
		int distance = width - top.getX();
		int third = distance / 3;
		int x1 = top.getX() + third;

		System.out.println("distance : " + distance + " third : "
			+ third + " x1 : " + x1);
		angle = calcAngleFromTop(x1, top.getX());

		// check for long side or short side
		if (width < height) {
		    // CNI add 90 degrees
		    angle += 90;
		}
		return angle;
	    } else {
		System.out
			.println("calcul angle : NOT top.getX() <= getHalf(width) ");
		int x1 = top.getX() / 3;
		System.out.println(" x1 : " + x1);
		angle = calcAngleFromTop(x1, top.getX());

		// check for long side or short side
		if (width < height) {
		    // CNI add 90 degrees
		    angle += 90;
		}
		return -angle;
	    }

	    // } else { // close to the right
	    // System.out.println("calcul angle : close to the right ");
	    // int third = top.getX() / 3;
	    // int x1 = third;
	    // int x2 = third + third;
	    // // TODO : check for long side or short side
	    // // TODO : possibly add 90 degrees
	    // // negate the angle to rotate the other way
	    // System.out.println("third : " + third + " x1 : " + x1 + " x2 : "
	    // + x2);
	    // return -calcAngleFromTop(x1, x2);
	    //
	    // }
	}

	else if (bestCorner.isRight()) {
	    Coord right = corners.getRight();
	    if (right.getY() <= getHalf(height)) {
		System.out
			.println("calcul angle : right.getY() <= getHalf(height) ");
		int distance = height - right.getY();
		int third = distance / 3;
		int y1 = right.getY() + third;

		System.out.println("distance : " + distance + " third : "
			+ third + " y1 : " + y1);
		angle = calcAngleFromRight(y1, right.getY());

		// check for long side or short side
		if (width < height) {
		    // CNI add 90 degrees
		    angle += 90;
		}
		return angle;
	    } else {
		System.out
			.println("calcul angle : NOT right.getY() <= getHalf(height) ");
		int y1 = right.getY() / 3;
		System.out.println(" y1 : " + y1);
		angle = calcAngleFromRight(y1, right.getY());

		// check for long side or short side
		if (width < height) {
		    // CNI add 90 degrees
		    angle += 90;
		}
		return -angle;
	    }
	}

	else if (bestCorner.isBottom()) {
	    Coord bottom = corners.getBottom();
	    if (bottom.getX() <= getHalf(width)) {
		System.out
			.println("calcul angle : bottom.getX() <= getHalf(width) ");
		int distance = width - bottom.getX();
		int third = distance / 3;
		int x1 = bottom.getX() + third;

		System.out.println("distance : " + distance + " third : "
			+ third + " x1 : " + x1);
		angle = calcAngleFromBottom(x1, bottom.getX());

		// check for long side or short side
		if (width < height) {
		    // CNI add 90 degrees
		    angle += 90;
		}
		return -angle;
	    } else {
		System.out
			.println("calcul angle : NOT bottom.getX() <= getHalf(width) ");
		int x1 = bottom.getX() / 3;
		System.out.println(" x1 : " + x1);
		angle = calcAngleFromBottom(x1, bottom.getX());

		// check for long side or short side
		if (width < height) {
		    // CNI add 90 degrees
		    angle += 90;
		}
		return angle;
	    }

	}

	else {
	    assert bestCorner.isLeft();
	    Coord left = corners.getLeft();
	    if (left.getY() <= getHalf(height)) {
		System.out
			.println("calcul angle : left.getY() <= getHalf(height) ");
		int distance = height - left.getY();
		int third = distance / 3;
		int y1 = left.getY() + third;

		System.out.println("distance : " + distance + " third : "
			+ third + " y1 : " + y1);
		angle = calcAngleFromLeft(y1, left.getY());

		// check for long side or short side
		if (width < height) {
		    // CNI add 90 degrees
		    angle += 90;
		}
		return -angle;
	    } else {
		System.out
			.println("calcul angle : NOT left.getY() <= getHalf(height) ");
		int y1 = left.getY() / 3;
		System.out.println(" y1 : " + y1);
		angle = calcAngleFromLeft(y1, left.getY());

		// check for long side or short side
		if (width < height) {
		    // CNI add 90 degrees
		    angle += 90;
		}
		return angle;
	    }

	}
    }

    private double calcAngleFromTop(int x1, int x) {
	// but : trouver y1 tq x, x1, y1 forment un triangle rectangle et arc
	// tangente theta = y / abs(x1 - x)
	int y1 = -1;
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
	    if (grayTotal > avgGrayTolerance * pixelMargin * 2) {
		y1 = y;
		break;
	    }
	}

	// int second = -1;
	// for (int y = 0; y < height; y++) {
	// int grayTotal = 0;
	// for (int i = x2 - pixelMargin; i < x2 + pixelMargin; i++) {
	// grayTotal += new GrayColor(image.getRGB(i, y)).getGrayscale();
	// }
	// if (grayTotal > avgGrayTolerance * pixelMargin * 2) {
	// second = y;
	// break;
	// }
	// }

	System.out.println("y1 : " + y1);
	// double opposite = second - first;
	// double adjacent = x2 - x1;

	double opposite = y1;
	double adjacent = Math.abs(x1 - x);

	return Math.toDegrees(Math.atan(opposite / adjacent));
    }

    private double calcAngleFromBottom(int x1, int x) {
	// but : trouver y1 tq x, x1, y1 forment un triangle rectangle et arc
	// tangente theta = height - y / abs(x1 - x)
	int y1 = -1;
	for (int y = height; y > 0; y--) {
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
	    if (grayTotal > avgGrayTolerance * pixelMargin * 2) {
		y1 = y;
		break;
	    }
	}

	System.out.println("y1 : " + y1);
	double opposite = height - y1;
	double adjacent = Math.abs(x1 - x);

	return Math.toDegrees(Math.atan(opposite / adjacent));
    }

    private double calcAngleFromRight(int y1, int y) {
	// but : trouver x1 tq y, y1, x1 forment un triangle rectangle et arc
	// tangente theta = width - x1 / abs(y1 - y)
	int x1 = -1;
	for (int x = width; x > 0; x--) {
	    int grayTotal = 0;
	    for (int i = y1 - pixelMargin; i < y1 + pixelMargin; i++) {
		try {
		    grayTotal += new GrayColor(image.getRGB(i, x))
			    .getGrayscale();
		} catch (RuntimeException e) {
		    System.out.println("y1=" + y1 + "  i=" + i);
		    throw e;
		}
	    }
	    if (grayTotal > avgGrayTolerance * pixelMargin * 2) {
		x1 = x;
		break;
	    }
	}

	System.out.println("x1 : " + x1);
	double opposite = width - x1;
	double adjacent = Math.abs(y1 - y);

	return Math.toDegrees(Math.atan(opposite / adjacent));
    }

    private double calcAngleFromLeft(int y1, int y) {
	// but : trouver x1 tq y, y1, x1 forment un triangle rectangle et arc
	// tangente theta = x1 / (y1 - y)
	int x1 = -1;
	for (int x = 0; x < width; x++) {
	    int grayTotal = 0;
	    for (int i = y1 - pixelMargin; i < y1 + pixelMargin; i++) {
		try {
		    grayTotal += new GrayColor(image.getRGB(i, x))
			    .getGrayscale();
		} catch (RuntimeException e) {
		    System.out.println("y1=" + y1 + "  i=" + i);
		    throw e;
		}
	    }
	    if (grayTotal > avgGrayTolerance * pixelMargin * 2) {
		x1 = x;
		break;
	    }
	}

	System.out.println("x1 : " + x1);
	double opposite = x1;
	double adjacent = Math.abs(y1 - y);

	return Math.toDegrees(Math.atan(opposite / adjacent));
    }

    /**
     * Returns the number halfed (or half rounded up if <code>num</code> is
     * odd).
     * <p>
     * For example, if <code>num</code> is 10 this method will return 5 (ie. num
     * / 2), and if <code>num</code> is 9 this method will return 5 (ie. (num +
     * 1) / 2).
     * 
     * @param num
     *            something non-negative to half
     * @return the number halfed (or half rounded up if <code>num</code> is odd)
     */
    private int getHalf(int num) {
	assert num >= 0 : "We don't do negatives : " + num;

	boolean even = num % 2 == 0;
	int halfExactly = num / 2;
	// don't lose anything via an implicit int cast
	int halfRoundedUp = (num + 1) / 2;
	return even ? halfExactly : halfRoundedUp;
    }

}
