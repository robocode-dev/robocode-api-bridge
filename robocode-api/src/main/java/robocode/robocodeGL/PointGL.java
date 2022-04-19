package robocode.robocodeGL;

import gl4java.GLFunc;

import java.awt.*;

/**
 * Dummy class that stub out RobocodeGL 0.1.4 functionality.
 *
 * Original author is David Alves for RobocodeGL.
 */
@SuppressWarnings("unused") // API
public class PointGL extends RenderElement {

	public void setSize(float size) {}

	public synchronized void setPosition(double x, double y) {}

	public void setColor(Color c) {}

	public float getStringX() {
		return 0f;
	}

	public float getStringY() {
		return 0f;
	}

	public void draw(GLFunc gl) {}
}
