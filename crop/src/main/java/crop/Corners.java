package crop;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.StringUtils;

/**
 * Finds the corners of an image.
 */
public class Corners {
	private Coord top;
	private Coord right;
	private Coord bottom;
	private Coord left;

	private int topIndex;
	private int rightIndex;
	private int bottomIndex;
	private int leftIndex;

	private BufferedImage image;
	private int width;
	private int height;

	private int[] xTopRow;
	private int[] yRightCol;
	private int[] xBottomRow;
	private int[] yLeftCol;
	private int lastRow;
	private int lastCol;

	private BestCorner bestCorner;

	/**
	 * Finds the corners of an image.
	 * 
	 * @param image
	 */
	public Corners(BufferedImage image) {
		if (image == null) {
			return;
		}
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();

		initEdges();
		findCorners();
		calcBestCorner();
		calcCoords();
	}

	/**
	 * Init grays for extremity rows/columns.
	 * 
	 * @param image
	 * @return
	 */
	private void initEdges() {
		// top and bottom edges
		xTopRow = new int[width];
		xBottomRow = new int[width];
		lastRow = height - 1;

		for (int x = 0; x < width; x++) {
			xTopRow[x] = new GrayColor(image.getRGB(x, 0)).getGrayscale();
			xBottomRow[x] = new GrayColor(image.getRGB(x, lastRow)).getGrayscale();
		}

		// left and right edges
		yLeftCol = new int[height];
		yRightCol = new int[height];
		lastCol = width - 1;

		for (int y = 0; y < height; y++) {
			yLeftCol[y] = new GrayColor(image.getRGB(0, y)).getGrayscale();
			yRightCol[y] = new GrayColor(image.getRGB(lastCol, y)).getGrayscale();
		}
	}

	/**
	 * Search for each corner, one by one.
	 */
	private void findCorners() {
		System.out.println("\n\nfinding topCorner");
		topIndex = findCorner(xTopRow);
		System.out.println("topIndex : " + topIndex);

		System.out.println("\n\nfinding rightCorner");
		rightIndex = findCorner(yRightCol);
		System.out.println("rightIndex : " + rightIndex);

		System.out.println("\n\nfinding bottomCorner");
		bottomIndex = findCorner(xBottomRow);
		System.out.println("bottomIndex : " + bottomIndex);

		System.out.println("\n\nfinding leftCorner");
		leftIndex = findCorner(yLeftCol);
		System.out.println("leftIndex : " + leftIndex);
	}

	/**
	 * Find the darkest part of each array which should be a corner.
	 * 
	 * @param array
	 * @return
	 */
	private int findCorner(int[] array) {
		int length = array.length;
		int half = getHalf(length);

		int leftIndexBegin = 0;
		int leftIndexEnd = leftIndexBegin + half;

		int rightIndexBegin = length - half;
		int rightIndexEnd = array.length;

		boolean equalTotals = false;
		int cornerIndex = -1;
		// Loop will stop when equalTotals OR length == 3 OR length == 2
		for (int leftTotal = 0, rightTotal = 0; !equalTotals && length > 1; leftTotal = 0, rightTotal = 0) {
			System.out.format("length=%d, leftIndexBegin=%d, leftIndexEnd=%d, rightIndexBegin=%d, rightIndexEnd=%d%n",
					length, leftIndexBegin, leftIndexEnd, rightIndexBegin, rightIndexEnd);
			for (int i = leftIndexBegin; i < leftIndexEnd; i++) {
				leftTotal += array[i];
			}

			for (int i = rightIndexBegin; i < rightIndexEnd; i++) {
				rightTotal += array[i];
			}

			equalTotals = leftTotal == rightTotal;

			if (!equalTotals && length > 4) {
				length = getHalf(length); // half old length
				half = getHalf(length); // half new length

				if (leftTotal < rightTotal) { // left half is darker
					System.out.format("Left is darker : %d < %d%n", leftTotal, rightTotal);
					// leftIndexBegin stays the same
					leftIndexEnd = leftIndexBegin + half;
					rightIndexEnd = leftIndexBegin + length;
					rightIndexBegin = rightIndexEnd - half;

				} else { // right half is darker
					System.out.format("Right is darker : %d > %d%n", leftTotal, rightTotal);
					// rightIndexEnd stays the same
					rightIndexBegin = rightIndexEnd - half;
					leftIndexBegin = rightIndexEnd - length;
					leftIndexEnd = leftIndexBegin + half;
				}
			} else {
				System.out.format("Targetting a corner : %d, %d%n", leftTotal, rightTotal);
				for (int i = leftIndexBegin; i < rightIndexEnd; i++) {
					System.out.print(StringUtils.leftPad("" + array[i], 3, ' '));
					System.out.print(", ");
				}
				System.out.println();

				System.out.println("equalTotals : " + equalTotals);
				System.out.println("length : " + length);

				if (equalTotals) {
					cornerIndex = leftIndexBegin + half;

				} else if (leftTotal < rightTotal) { // left half is darker {
					cornerIndex = leftIndexBegin + getHalf(half);

				} else { // right half is darker
					cornerIndex = rightIndexEnd - getHalf(half);
				}
				break;
			}
		}

		return cornerIndex;
	}

	/**
	 * The best corner is the one closest to any corner of the image.
	 */
	private void calcBestCorner() {
		int topDistance = Math.min(topIndex, width - topIndex);
		int bottomDistance = Math.min(bottomIndex, width - bottomIndex);

		int leftDistance = Math.min(leftIndex, height - leftIndex);
		int rightDistance = Math.min(rightIndex, height - rightIndex);

		if (isLowest(topDistance, leftDistance, bottomDistance, rightDistance)) {
			bestCorner = BestCorner.TOP;

		} else if (isLowest(leftDistance, bottomDistance, rightDistance, topDistance)) {
			bestCorner = BestCorner.LEFT;

		} else if (isLowest(bottomDistance, rightDistance, topDistance, leftDistance)) {
			bestCorner = BestCorner.BOTTOM;

		} else {
			assert isLowest(rightDistance, topDistance, leftDistance, bottomDistance);
			bestCorner = BestCorner.RIGHT;
		}
	}

	/**
	 * Checks to see if value is the <code>lowest</code> among all the other
	 * values. Other values could also be equally the lowest. For eg. :
	 * 
	 * <pre>
	 * <code>
	 * isLowest( 1,  2, 3, 4 ); // true
	 * isLowest( 1,  1, 1, 1 ); // true
	 * isLowest( 4,  3, 2, 1 ); // false
	 * isLowest( 2,  2, 2, 1 ); // false
	 * </code>
	 * </pre>
	 * 
	 * @param value
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	private boolean isLowest(int value, int a, int b, int c) {
		return value <= a && value <= b && value <= c;
	}

	private void calcCoords() {
		top = new Coord(topIndex, 0);
		bottom = new Coord(bottomIndex, lastRow);

		left = new Coord(0, leftIndex);
		right = new Coord(lastCol, rightIndex);
	}

	/** An x, y coordinate. */
	public class Coord {
		private int x, y;
		private String rep;

		private Coord(int x, int y) {
			this.x = x;
			this.y = y;
			rep = String.format("%d, %d", x, y);
		}

		/** @return the x coordinate. */
		public int getX() {
			return x;
		}

		/** @return the y coordinate. */
		public int getY() {
			return y;
		}

		@Override
		public String toString() {
			return rep;
		}
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

	/** @return the right corner coordinate. */
	public Coord getRight() {
		return right;
	}

	/** @return the top corner coordinate. */
	public Coord getTop() {
		return top;
	}

	/** @return the bottom corner coordinate. */
	public Coord getBottom() {
		return bottom;
	}

	/** @return the left corner coordinate. */
	public Coord getLeft() {
		return left;
	}

	/**
	 * The best corner is the one closest to any corner of the image.
	 * 
	 * @return
	 */
	public BestCorner getBestCorner() {
		return bestCorner;
	}

	public enum BestCorner {
		TOP, RIGHT, BOTTOM, LEFT;

		public boolean isTop() {
			return this == TOP;
		}

		public boolean isRight() {
			return this == RIGHT;
		}

		public boolean isBottom() {
			return this == BOTTOM;
		}

		public boolean isLeft() {
			return this == LEFT;
		}
	}

}
