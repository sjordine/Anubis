package br.unicamp.ic.anubis.util;

import java.awt.Color;

public class ColorUtil {

	public static Color getGradientColor(int value, int startValue,
			int endValue, Color startRGBColor, Color endRGBColor,
			int transparency) {
		Color color = null;
		

		if (startValue > endValue) {
			int tmp = endValue;
			endValue = startValue;
			startValue = tmp;
		}
		

		if (value >= startValue && value <= endValue) {
			// Get deltas
			double delta = endValue - startValue;
			double rDelta = endRGBColor.getRed() - startRGBColor.getRed();
			double gDelta = endRGBColor.getGreen() - startRGBColor.getGreen();
			double bDelta = endRGBColor.getBlue() - startRGBColor.getBlue();
			// Get RGB step
			double rStep = rDelta / delta;
			double gStep = gDelta / delta;
			double bStep = bDelta / delta;
			// Get number of steps
			int steps = value - startValue;
			// Create color
			int r = startRGBColor.getRed() + (int) (steps * rStep);
			int g = startRGBColor.getGreen() + (int) (steps * gStep);
			int b = startRGBColor.getBlue() + (int) (steps * bStep);
			color = new Color(r, g, b, transparency);
		}

		return color;
	}

}
