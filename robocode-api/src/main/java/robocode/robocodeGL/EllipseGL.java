package robocode.robocodeGL;

import gl4java.GLFunc;
import java.awt.Color;

/**
 * Dummy class that stub out RobocodeGL 0.1.4 functionality.
 *
 * Original author is David Alves for RobocodeGL.
 */
@SuppressWarnings("unused") // API
public class EllipseGL extends RenderElement {
	
	public EllipseGL() {}

	public EllipseGL(double x, double y, double width, double height) {}

	public EllipseGL(double x, double y, double width, double height, Color color, double lineWidth) {}
	
	public void setFrame(double x, double y, double width, double height) {}
	
	public void setLineWidth(double lineWidth) {}

	public void setFilled(boolean filled) {}

	public void setColor(Color c) {}
	
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
