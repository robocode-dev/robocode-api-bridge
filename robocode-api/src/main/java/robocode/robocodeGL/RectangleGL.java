package robocode.robocodeGL;

import gl4java.GLFunc;
import java.awt.Color;

/**
 * Dummy class that stub out RobocodeGL 0.1.4 functionality.
 *
 * Original author is David Alves for RobocodeGL.
 */
public class RectangleGL extends RenderElement {
	
	public RectangleGL() {}

	public RectangleGL(double x, double y, double width, double height) {}

	public RectangleGL(double x, double y, double width, double height, Color c, double lineWidth) {}

	public void setBounds(double x, double y, double width, double height) {}

	public void setLineWidth(double lineWidth) {}

	public void setColor(Color color) {}

	public void setFilled(boolean filled) {}

	public void setLocation(double x, double y) {}

	public void setSize(double width, double height) {}

	public void draw(GLFunc gl) {}

	public float getStringX() {
		return 0f;
	}

	public float getStringY() {
		return 0f;
	}
}
