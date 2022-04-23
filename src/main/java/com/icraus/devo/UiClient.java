package com.icraus.devo;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class UiClient extends Application{

    private RobotView view;
    private ObservablePose pose;
    private Integer rotation = 0;

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
        view = new RobotView((int)widthField.getValue(), (int)depthField.getValue());
        pose = new ObservablePose();
        pose.setHook(() -> {
//            Platform.runLater(()->{
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
        });

        view.currentXIndexProperty().bindBidirectional(pose.xProperty());
        view.currentYIndexProperty().bindBidirectional(pose.yProperty());
        view.rotationProperty().bindBidirectional(pose.rotationProperty());
        pose.setX(1);
        pose.setY(2);
        pose.setOrient(RobotController.NORTH);

        createMapButton.setOnAction(e -> {
            view.setBoardDepth((Integer) depthField.getValue());
            view.setBoardWidth((Integer) widthField.getValue());
        });
        IRobotController controller = new RobotController(5, 5);
        Button b2 = new Button("Move");
        b2.setOnAction(e -> {
            try {
                view.startDrawing();
                controller.executeRoute(pose, routeField.getText());
                view.endDrawing();
            } catch (UnSupportedMoveOperationException unSupportedMoveOperationException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(unSupportedMoveOperationException.getMessage());
                alert.showAndWait();
                unSupportedMoveOperationException.printStackTrace();
            }
        });
        box.getChildren().add(b2);
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