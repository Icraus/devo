package com.icraus.devo;


import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class UiClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    Spinner depthField = new Spinner(0, 20, 0);
    Spinner widthField = new Spinner(0, 20, 0);
    TextField routeField = new TextField();
    BooleanProperty canvasChanged = new SimpleBooleanProperty(false);

    private Pane createLabeledWidget(Pane box, String labelName, Control control){
        Label label = new Label(labelName);
        HBox hBox = new HBox();
        label.setLabelFor(control);
        hBox.getChildren().add(label);
        hBox.getChildren().add(control);
        hBox.setSpacing(20);
        box.getChildren().add(hBox);
        return hBox;
    }
    public Pane createUI(){
        HBox box = new HBox();
        var depthPane = createLabeledWidget(box, "Depth", depthField);
        var widthPane = createLabeledWidget(box, "Width", widthField);
        var routePane = createLabeledWidget(box, "Route", routeField);
        box.setSpacing(50);
        Button createMapButton = new Button("Draw Map");

        box.getChildren().add(createMapButton);
        BorderPane root = new BorderPane();
        root.setTop(box);
        widthField.increment(5);
        depthField.increment(5);
        RobotView view = new RobotView((int)widthField.getValue(), (int)depthField.getValue());
        createMapButton.setOnAction(e -> {
            view.boardDepthProperty().set((Integer) depthField.getValue());
            view.boardWidthProperty().set((Integer) widthField.getValue());
        });
        root.setCenter(view);
        return root;
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Devo Robot!");
        var root = createUI();
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}