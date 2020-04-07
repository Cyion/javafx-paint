package paint.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class CanvasPaintWrapper extends ScrollPane {
    private Canvas canvas = new Canvas(1600, 850);
    private GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();

    public CanvasPaintWrapper() {
        setTool(Tools.BRUSH); //TODO: Maybe remove this?
        clearCanvas();
        this.setContent(this.canvas);
    }

    /**
     * Sets the painting color.
     * @param color painting color
     */
    public void setColor(Color color) {
        this.graphicsContext.setStroke(color);
        this.graphicsContext.setFill(color);
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
     * Clears the painting canvas.
     */
    public void clearCanvas() {
        this.graphicsContext.setFill(Color.WHITE);
        this.graphicsContext.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    }

    /**
     * Resizes the painting canvas.
     * @param width
     * @param height
     */
    public void resizeCanvas(double width, double height) {
        this.canvas.setHeight(height);
        this.canvas.setWidth(width);
        clearCanvas();
    }

    /**
     * Saves the painting as a .png file.
     */
    public void save() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) this.canvas.getWidth(), (int) this.canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {

            }
        }
    }

    public void setTool(CanvasPaintWrapper.Tools tool) {
        switch (tool) {
            case BRUSH:
                this.canvas.setOnMousePressed(e -> this.graphicsContext.beginPath());
                this.canvas.setOnMouseDragged(e -> draw(e.getX(), e.getY()));
                this.canvas.setOnMouseReleased(e -> this.graphicsContext.closePath());
                this.canvas.setOnMouseClicked(null);
                break;
        }
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

    public enum Tools {
        BRUSH
    }
}
