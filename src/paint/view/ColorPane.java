package paint.view;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class ColorPane extends GridPane {
    private CanvasPaintWrapper wrapper;
    private List<Button> buttons = new ArrayList<>(); //TODO trim the list to actual color size (e.g. maxRow and maxColumn)
    private Orientation orientation = Orientation.ColumnSpan;
    private int maxRow = Integer.MAX_VALUE;
    private int maxColumn = Integer.MAX_VALUE;

    public ColorPane(CanvasPaintWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * Adds a new color button to the pane, with respect to the orientation and the column/row constraints.
     * @param color If this is null, a dummy button will be added to the pane (with no effect).
     */
    public void addColor(Color color) {
        Canvas canvas = new Canvas(20, 20);

        Button colorButton = new Button();
        colorButton.setPadding(new Insets(3, 3, 3, 3));
        colorButton.setGraphic(canvas);

        if (color != null) {
            canvas.getGraphicsContext2D().setFill(color);
            canvas.getGraphicsContext2D().fillRect(0, 0, 20, 20);
            colorButton.setOnAction(e -> wrapper.setColor(color));
        }
        this.buttons.add(colorButton);
        refreshButtons();
    }

    /**
     * Sets the orientation of the buttons.
     * @param orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        refreshButtons();
    }

    /**
     * Sets max columns of colors to be displayed.
     * If more colors are added than can be displayed, the oldest color will be replaced.
     * @param maxColumn
     */
    public void setMaxColumns(int maxColumn) {
        this.maxColumn = maxColumn - 1 < 0 ? 0 : --maxColumn;
    }

    /**
     * Sets max rows of colors to be displayed.
     * If more colors are added than can be displayed, the oldest color will be replaced.
     * @param maxRow
     */
    public void setMaxRows(int maxRow) {
        this.maxRow = maxRow - 1 < 0 ? 0 : --maxRow;
    }

    /**
     * Refreshes the buttons in the pane with respect to the orientation and column/row constraints.
     */
    private void refreshButtons() {
        this.getChildren().clear();
        int row  = 0;
        int column = 0;
        for (Button button : this.buttons) {
            this.add(button, column, row);
            if (this.orientation == Orientation.ColumnSpan) {
                if (column < this.maxColumn) {
                    column++;
                } else {
                    column = 0;
                }
            } else if (this.orientation == Orientation.RowSpan) {
                if (row < this.maxRow) {
                    row++;
                } else {
                    row = 0;
                }
            } else {
                if (column < this.maxColumn) {
                    column ++;
                } else {
                    if (row < this.maxRow) {
                        row++;
                        column = 0;
                    } else {
                        row = 0;
                        column = 0;
                    }
                }
            }
        }
    }

    public enum Orientation {
        ColumnSpan, RowSpan, ColumnRowSpan
    }
}
