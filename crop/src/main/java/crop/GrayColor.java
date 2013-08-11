package crop;

import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 * Adds a way to get the level of gray from a color.
 * 
 * @see #getGrayscale()
 * @see #getGrayscaleLuminance()
 */
public class GrayColor extends Color {
	private static final long serialVersionUID = -7896893660248703132L;

	/** @see Color#Color(ColorSpace, float[], float) */
	public GrayColor(ColorSpace cspace, float[] components, float alpha) {
		super(cspace, components, alpha);
	}

	/** @see Color#Color(float, float, float) */
	public GrayColor(float r, float g, float b) {
		super(r, g, b);
	}

	/** @see Color#Color(float, float, float, float) */
	public GrayColor(float r, float g, float b, float a) {
		super(r, g, b, a);
	}

	/** @see Color#Color(int) */
	public GrayColor(int rgb) {
		super(rgb);
	}

	/** @see Color#Color(int, boolean) */
	public GrayColor(int rgba, boolean hasalpha) {
		super(rgba, hasalpha);
	}

	/** @see Color#Color(int, int, int) */
	public GrayColor(int r, int g, int b) {
		super(r, g, b);
	}

	/** @see Color#Color(int, int, int, int) */
	public GrayColor(int r, int g, int b, int a) {
		super(r, g, b, a);
	}

	/**
	 * Returns the <b>rounded</b> average of the colors : ({@link #getRed() red}
	 * + {@link #getGreen() green} + {@link #getBlue() blue}) / 3.
	 * <p>
	 * Alpha is ignored.
	 * 
	 * @return
	 */
	public int getGrayscale() {
		return (int) Math.round((getRed() + getGreen() + getBlue()) / 3.0);
	}

	/**
	 * Returns the <b>rounded</b> average of the colors using the luminance
	 * method : (0.30 * {@link #getRed() red} + 0.59 * {@link #getGreen() green}
	 * + 0.11 {@link #getBlue() blue}).
	 * <p>
	 * Alpha is ignored.
	 * 
	 * @return
	 */
	public int getGrayscaleLuminance() {
		return (int) Math.round((0.30 * getRed() + 0.59 * getGreen() + 0.11 * getBlue()));
	}
}
