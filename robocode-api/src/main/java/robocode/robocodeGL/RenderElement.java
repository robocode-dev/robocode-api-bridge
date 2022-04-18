package robocode.robocodeGL;

import gl4java.GLFont;
import gl4java.GLFunc;

/**
 * Dummy class that stub out RobocodeGL 0.1.4 functionality.
 *
 * Original author is David Alves for RobocodeGL.
 */
public abstract class RenderElement {

	public static void init(GLFont glf) {}

	public RenderElement() {}

	public synchronized void addLabel(LabelGL l) {}

	public synchronized void removeLabel(LabelGL l) {}
	
	public synchronized void remove() {}

	public synchronized boolean isRemoved() {
		return false;
	}

	public synchronized void drawStrings(GLFunc gl) {}

	public abstract void draw(GLFunc gl);
	
	public float getStringX() {
		return 0f;
	}

	public float getStringY() {
		return 0f;
	}
}
