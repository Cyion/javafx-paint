package paint.controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import paint.view.CanvasPaintWrapper;
import paint.view.ColorPane;
import paint.view.NewCanvasPane;

public class Controller extends Application {
    private BorderPane root = new BorderPane();
    private CanvasPaintWrapper wrapper = new CanvasPaintWrapper();
    private HBox toolPane = new HBox();
    private ColorPane colorPane = new ColorPane(this.wrapper);
    private ColorPane customColorPane = new ColorPane(this.wrapper);
    private ColorPicker colorPicker = new ColorPicker();
    private ComboBox<Integer> intensity = new ComboBox<>();
    private ComboBox<CanvasPaintWrapper.Tools> tools = new ComboBox<>();
    private NewCanvasPane newCanvasPane = new NewCanvasPane(this.wrapper);
    private Button saveButton = new Button("Speichern");

    public Controller() {
        this.colorPane.addColor(Color.RED);
        this.colorPane.addColor(Color.GREEN);
        this.colorPane.addColor(Color.BLUE);
        this.colorPane.addColor(Color.YELLOW);
        this.colorPane.addColor(Color.ORANGE);
        this.colorPane.addColor(Color.BROWN);
        this.colorPane.addColor(Color.BLACK);
        this.colorPane.addColor(Color.GRAY);
        this.colorPane.addColor(Color.WHITE);

        this.customColorPane.setMaxColumns(9);
        this.customColorPane.addColor(null);
        this.customColorPane.addColor(null);
        this.customColorPane.addColor(null);
        this.customColorPane.addColor(null);
        this.customColorPane.addColor(null);
        this.customColorPane.addColor(null);
        this.customColorPane.addColor(null);
        this.customColorPane.addColor(null);
        this.customColorPane.addColor(null);

        VBox colorPaneWrapper = new VBox();
        colorPaneWrapper.getChildren().addAll(this.colorPane, this.customColorPane);

        this.colorPicker.getStyleClass().add(ColorPicker.STYLE_CLASS_BUTTON);
        this.colorPicker.setOnAction(e -> {
            this.customColorPane.addColor(this.colorPicker.getValue());
            this.wrapper.setColor(this.colorPicker.getValue());
        });
        //TODO: ColorPicker should display the currently selected color (from the canvas). (Properties)

        this.intensity.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        this.intensity.getSelectionModel().selectFirst();
        this.intensity.valueProperty().addListener((observable, oldValue, newValue) -> this.wrapper.setLineWidth(observable.getValue()));

        this.tools.getItems().addAll(CanvasPaintWrapper.Tools.BRUSH);
        this.tools.getSelectionModel().selectFirst();
        this.tools.valueProperty().addListener(((observable, oldValue, newValue) -> this.wrapper.setTool(observable.getValue())));

        this.saveButton.setOnAction(e -> this.wrapper.save());

        this.toolPane.setSpacing(20);
        this.toolPane.setAlignment(Pos.CENTER_LEFT);
        this.toolPane.getChildren().addAll(colorPaneWrapper, this.colorPicker, this.intensity, this.tools, this.newCanvasPane, this.saveButton);

        this.root.setTop(this.toolPane);
        this.root.setCenter(this.wrapper);
    }

    public BorderPane getRoot() {
        return this.root;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Painting");
        primaryStage.setScene(new Scene(this.getRoot(), 1600, 900));
        primaryStage.getScene().getStylesheets().add("paint.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
