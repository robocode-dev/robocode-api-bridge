package dev.robocode.tankroyale.bridge;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import javax.swing.UIManager;

public class Graphics2DImpl extends Graphics2D {

    // Needed for getTransform()
    private transient AffineTransform transform = new AffineTransform();

    // Needed for getComposite()
    private transient Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);

    // Needed for getPaint()
    private transient Paint paint = Color.BLACK;

    // Needed for getStroke()
    private transient Stroke stroke = new BasicStroke();

    // Needed for getRenderingHint() and getRenderingHints()
    private transient RenderingHints renderingHints;

    // Needed for getBackground()
    private transient Color background = UIManager.getColor("Button.background");

    // Needed for getClip()
    private transient Shape clip; // is null initially

    // Needed for getColor()
    private transient Color color = Color.BLACK;

    // Needed for getFont()
    private transient Font font = new Font("Dialog", Font.PLAIN, 11); // used for robot labels

    // Flag indicating if this proxy has been initialized
    private transient boolean isInitialized;

    // The one and only constructor
    public Graphics2DImpl() {
        // Create a default RenderingHints object
        renderingHints = new RenderingHints(null);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
        renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
    }

    // --------------------------------------------------------------------------
    // Overriding all methods from the extended Graphics class
    // --------------------------------------------------------------------------

    // Methods that should not be overridden or implemented:
    // - finalize()
    // - toString()

    @Override
    public Graphics create() {
        Graphics2DImpl gfxProxyCopy = new Graphics2DImpl();
        gfxProxyCopy.transform = transform;
        gfxProxyCopy.composite = copyOf(composite);
        gfxProxyCopy.paint = paint;
        gfxProxyCopy.stroke = copyOf(stroke);
        gfxProxyCopy.renderingHints = renderingHints;
        gfxProxyCopy.background = copyOf(background);
        gfxProxyCopy.clip = copyOf(clip);
        gfxProxyCopy.color = copyOf(color);
        gfxProxyCopy.font = font;
        gfxProxyCopy.isInitialized = isInitialized;
        return gfxProxyCopy;
    }

    @Override
    public Graphics create(int x, int y, int width, int height) {
        Graphics g = create();
        g.translate(x, y);
        g.setClip(0, 0, width, height);
        return g;
    }

    @Override
    public void translate(int x, int y) {
        // for getTransform()
        this.transform.translate(x, y);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color c) {
        // for getColor()
        this.color = c;
    }

    @Override
    public void setPaintMode() {
    }

    @Override
    public void setXORMode(Color c1) {
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        // for getFont()
        this.font = font;
    }

    @Override
    public FontMetrics getFontMetrics(Font f) {
        return new FontMetricsByFont(f, getFontRenderContext());
    }

    @Override
    public Rectangle getClipBounds() {
        return clip.getBounds();
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {
        // for getClip()
        Area clipArea = new Area(clip);
        Area clipRectArea = new Area(new Rectangle(x, y, width, height));
        clipArea.intersect(clipRectArea);
        this.clip = clipArea;
    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        // for getClip()
        this.clip = new Rectangle(x, y, width, height);
    }

    @Override
    public Shape getClip() {
        return clip;
    }

    @Override
    public void setClip(Shape clip) {
        // for getClip()
        this.clip = clip;
    }

    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
    }

    @Override
    public void clearRect(int x, int y, int width, int height) {
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
    }

    @Override
    public void draw3DRect(int x, int y, int width, int height, boolean raised) {
    }

    @Override
    public void fill3DRect(int x, int y, int width, int height, boolean raised) {
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
    }

    @Override
    public void fillOval(int x, int y, int width, int height) {
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
    }

    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int npoints) {
    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int npoints) {
    }

    @Override
    public void drawPolygon(Polygon p) {
    }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int npoints) {
    }

    @Override
    public void fillPolygon(Polygon p) {
    }

    @Override
    public void drawString(String str, int x, int y) {
        if (str == null) {
            throw new NullPointerException("str is null"); // According to the specification!
        }
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y) {
    }

    @Override
    public void drawBytes(byte[] data, int offset, int length, int x, int y) {
    }

    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return true;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return true;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return true;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return true;
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return true;
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        return true;
    }

    @Override
    public void dispose() { // Ignored here
    }

    @Override
    @Deprecated
    public Rectangle getClipRect() {
        return getClipBounds(); // Must use getClipBounds() instead of this deprecated method
    }

    @Override
    public boolean hitClip(int x, int y, int width, int height) {
        return (clip != null) && clip.intersects(x, y, width, height);
    }

    @Override
    public Rectangle getClipBounds(Rectangle r) {
        Rectangle bounds = clip.getBounds();

        r.setBounds(bounds);
        return bounds;
    }

    // --------------------------------------------------------------------------
    // Overriding all methods from the extended Graphics2D class
    // --------------------------------------------------------------------------

    @Override
    public void draw(Shape s) {
    }

    @Override
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return false; // as if the image is still being rendered (as the call is queued)
    }

    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
    }

    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
    }

    @Override
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
    }

    @Override
    public void drawString(String str, float x, float y) {
        if (str == null) {
            throw new NullPointerException("str is null"); // According to the specification!
        }
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
    }

    @Override
    public void drawGlyphVector(GlyphVector gv, float x, float y) {
    }

    @Override
    public void fill(Shape s) {
    }

    @Override
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        if (onStroke && getStroke() != null) {
            s = getStroke().createStrokedShape(s);
        }

        if (getTransform() != null) {
            s = getTransform().createTransformedShape(s);
        }

        Area area = new Area(s);

        if (getClip() != null) {
            area.intersect(new Area(getClip()));
        }

        return area.intersects(rect);
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    @Override
    public void setComposite(Composite comp) {
        // for getComposite()
        this.composite = comp;
    }

    @Override
    public void setPaint(Paint paint) {
        // for getPaint()
        this.paint = paint;
    }

    @Override
    public void setStroke(Stroke s) {
        // for getStroke()
        this.stroke = s;
    }

    @Override
    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
        // for getRenderingHint() and getRenderingHints()
        this.renderingHints.put(hintKey, hintValue);
    }

    @Override
    public Object getRenderingHint(RenderingHints.Key hintKey) {
        return renderingHints.get(hintKey);
    }

    @Override
    public void setRenderingHints(Map<?, ?> hints) {
        // for getRenderingHint() and getRenderingHints()
        this.renderingHints.clear(); // Needs to clear first
        this.renderingHints.putAll(hints); // Only overrides existing keys
    }

    @Override
    public void addRenderingHints(Map<?, ?> hints) {
        // for getRenderingHint() and getRenderingHints()
        this.renderingHints.putAll(hints);
    }

    @Override
    public RenderingHints getRenderingHints() {
        return renderingHints;
    }

    @Override
    public void translate(double tx, double ty) {
        // for getTransform()
        transform.translate(tx, ty);
    }

    @Override
    public void rotate(double theta) {
        // for getTransform()
        transform.rotate(theta);
    }

    @Override
    public void rotate(double theta, double x, double y) {
        // for getTransform()
        transform.rotate(theta, x, y);
    }

    @Override
    public void scale(double sx, double sy) {
        // for getTransform()
        transform.scale(sx, sy);
    }

    @Override
    public void shear(double shx, double shy) {
        // for getTransform()
        transform.shear(shx, shy);
    }

    @Override
    public void transform(AffineTransform Tx) {
        // for getTransform()
        transform.concatenate(Tx);
    }

    @Override
    public void setTransform(AffineTransform Tx) {
        // for getTransform()
        this.transform = Tx;
    }

    @Override
    public AffineTransform getTransform() {
        return (AffineTransform) transform.clone();
    }

    @Override
    public Paint getPaint() {
        return paint;
    }

    @Override
    public Composite getComposite() {
        return composite;
    }

    @Override
    public void setBackground(Color color) {
        // for getBackground()
        background = color;
    }

    @Override
    public Color getBackground() {
        return background;
    }

    @Override
    public Stroke getStroke() {
        return stroke;
    }

    @Override
    public void clip(Shape s) {
        // for getClip()
        if (s == null) {
            this.clip = null;
        } else {
            Area shapeArea = new Area(s);
            Area clipArea = new Area(clip);

            shapeArea.transform(transform); // transform by the current transform
            clipArea.intersect(shapeArea); // intersect current clip by the transformed shape

            this.clip = clipArea;
        }
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        RenderingHints hints = getRenderingHints();

        if (hints == null) {
            return new FontRenderContext(null, false, false);
        } else {
            boolean isAntiAliased = RenderingHints.VALUE_FRACTIONALMETRICS_ON.equals(hints.get(RenderingHints.KEY_TEXT_ANTIALIASING));
            boolean usesFractionalMetrics = RenderingHints.VALUE_FRACTIONALMETRICS_ON.equals(hints.get(RenderingHints.KEY_FRACTIONALMETRICS));

            return new FontRenderContext(null, isAntiAliased, usesFractionalMetrics);
        }
    }

    // --------------------------------------------------------------------------
    // Copy
    // --------------------------------------------------------------------------

    private static Color copyOf(Color c) {
        return (c != null) ? new Color(c.getRGB(), true) : null;
    }

    private Shape copyOf(Shape s) {
        return (s != null) ? new GeneralPath(s) : null;
    }

    private Stroke copyOf(Stroke s) {
        if (s == null) {
            return null;
        }
        if (s instanceof BasicStroke) {
            BasicStroke bs = (BasicStroke) s;

            return new BasicStroke(bs.getLineWidth(), bs.getEndCap(), bs.getLineJoin(), bs.getMiterLimit(), bs.getDashArray(), bs.getDashPhase());
        }
        throw new UnsupportedOperationException("The Stroke type '" + s.getClass().getName() + "' is not supported");
    }

    private Composite copyOf(Composite c) {
        if (c == null) {
            return null;
        }
        if (c instanceof AlphaComposite) {
            AlphaComposite ac = (AlphaComposite) c;

            return AlphaComposite.getInstance(ac.getRule(), ac.getAlpha());
        }
        throw new UnsupportedOperationException("The Composite type '" + c.getClass().getName() + "' is not supported");
    }

    // --------------------------------------------------------------------------
    // FontMetricsByFont class
    // --------------------------------------------------------------------------

    /**
     * Extended FontMetrics class which only purpose is to let us access its
     * protected contructor taking a Font as input parameter.
     *
     * @author Flemming N. Larsen
     */
    private static class FontMetricsByFont extends FontMetrics {
        static final long serialVersionUID = 1L;

        final FontRenderContext fontRenderContext;

        FontMetricsByFont(Font font, FontRenderContext frc) {
            super(font);
            fontRenderContext = frc;
        }

        // Bugfix [2791007] - FontMetrics StackOverflowError.
        // More info here: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=4257064
        @Override
        public int charsWidth(char[] data, int off, int len) {
            if (font == null) {
                return 0;
            }
            Rectangle2D bounds = font.getStringBounds(data, off, off + len, fontRenderContext);

            return (bounds != null) ? (int) (bounds.getWidth() + 0.5) : 0;
        }
    }
}