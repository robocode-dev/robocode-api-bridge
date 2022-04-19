package robocode.robocodeGL;

import gl4java.GLFunc;

import java.awt.*;

/**
 * Dummy class that stub out RobocodeGL 0.1.4 functionality.
 *
 * Original author is David Alves for RobocodeGL.
 */
@SuppressWarnings("unused") // API
public class LineGL extends RenderElement {

	public LineGL() {}

	public LineGL(double x1, double y1, double x2, double y2) {}

	public void setLine(double x1, double y1, double x2, double y2) {}

	public void setWidth(double lineWidth) {}

	public void setColor(Color c) {}

	public void draw(GLFunc gl) {}

	public float getStringX() {
		return 0f;
	}

	public float getStringY() {
		return 0f;
	}	
}
