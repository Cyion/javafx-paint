package paint.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class CanvasPaintWrapper extends ScrollPane {
    private Canvas canvas = new Canvas(1600, 900);;
    private GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();;

    public CanvasPaintWrapper() {
        this.canvas.setOnMousePressed(e -> this.graphicsContext.beginPath());
        this.canvas.setOnMouseDragged(e -> draw(e.getX(), e.getY()));
        this.canvas.setOnMouseReleased(e -> this.graphicsContext.closePath());
        clearCanvas();
        this.setContent(this.canvas);
    }

    /**
     * Sets the painting color.
     * @param color painting color
     */
    public void setColor(Color color) {
        this.graphicsContext.setStroke(color);
    }

    /**
     * Gets the painting color.
     * @return Painting color
     */
    public Color getColor() {
        return (Color) this.graphicsContext.getStroke();
    }

    /**
     * Sets the line width of the drawed lines.
     * @param width
     */
    public void setLineWidth(double width) {
        this.graphicsContext.setLineWidth(width);
    }

    /**
     * Clears the painting Canvas.
     */
    public void clearCanvas() {
        this.graphicsContext.setFill(Color.WHITE);
        this.graphicsContext.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    }

    /**
     * Draws a line to (x, y).
     * @param x coordinate of endpoint
     * @param y coordinate of endpoint
     */
    private void draw(double x, double y) {
        this.graphicsContext.setLineCap(StrokeLineCap.ROUND);
        this.graphicsContext.setLineJoin(StrokeLineJoin.ROUND);
        this.graphicsContext.lineTo(x, y);
        this.graphicsContext.stroke();
    }
}
