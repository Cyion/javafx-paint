package paint.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class NewCanvasPane extends HBox {
    private CanvasPaintWrapper wrapper;
    private NumberTextField widthInput = new NumberTextField();
    private NumberTextField heightInput = new NumberTextField();
    private Button create = new Button("Erstellen");

    public NewCanvasPane(CanvasPaintWrapper wrapper) {
        this.wrapper = wrapper;

        GridPane inputWrapper = new GridPane();
        inputWrapper.add(new Label("Breite: "), 0, 0);
        inputWrapper.add(new Label("Höhe: "), 0, 1);
        inputWrapper.add(this.widthInput, 1, 0);
        inputWrapper.add(this.heightInput, 1, 1);

        this.create.setOnAction(e -> {
            if (!this.widthInput.getText().equals("") && !this.heightInput.getText().equals("")) {
                this.wrapper.resizeCanvas(getInputWidth(), getInputHeight());
                this.widthInput.setText("");
                this.heightInput.setText("");
            }
        });

        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10);
        this.getChildren().addAll(inputWrapper, create);
    }

    private int getInputWidth() {
        return Integer.parseInt(this.widthInput.getText());
    }

    private int getInputHeight() {
        return Integer.parseInt(this.heightInput.getText());
    }
}
