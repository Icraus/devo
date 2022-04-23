package com.icraus.devo;

import javafx.animation.PathTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class RobotView extends BorderPane {
    private static final int BOX_SCALE = 100;
    private final IntegerProperty currentXIndex = new SimpleIntegerProperty();
    private final IntegerProperty currentYIndex = new SimpleIntegerProperty();
    private final IntegerProperty boardDepth = new SimpleIntegerProperty(5);
    private final IntegerProperty boardWidth = new SimpleIntegerProperty(5);
    private final IntegerProperty rotation = new SimpleIntegerProperty(0);
    private final BooleanProperty robotChanged = new SimpleBooleanProperty();
    final Canvas mainCanvas = new Canvas(500,500);
    final Canvas drawingCanvas = new Canvas(100,100);
    private PathTransition pathTransition = new PathTransition();
    Path path = new Path();
    Image image = new Image("/robot.png");
    private ScrollPane scrollPane = new ScrollPane();
    private Pane pane = new Pane();
    private int prevX;
    private int prevY;

    private RobotView(){
        widthProperty().addListener(e ->{
            updateCanvasSize();
        });
        heightProperty().addListener(e->{
            updateCanvasSize();
        });
        boardWidthProperty().addListener(e -> {
            canvasChanged(true);
            setRobotChanged(true);
        });
        boardDepthProperty().addListener(e -> {
            canvasChanged(true);
        });
        currentXIndexProperty().addListener(e->{
            setRobotChanged(false);
            setRobotChanged(true);
        });
        currentYIndexProperty().addListener(e->{
            setRobotChanged(false);
            setRobotChanged(true);
        });
        rotationProperty().addListener(e->{
            setRobotChanged(false);
            setRobotChanged(true);
        });

        robotChangedProperty().addListener(e ->{
            if(isRobotChanged()){
                drawRobot(currentXIndex.get(), currentYIndex.get(), rotation.get());
            }
        });
    }
    private void setCanvasSize(Canvas canvas, double width, double depth){
        canvas.setWidth(width);
        canvas.setHeight(depth);

    }

    public void setCurrentXIndex(int currentXIndex) {
        this.currentXIndex.set(currentXIndex);
    }

    public void setCurrentYIndex(int currentYIndex) {
        this.currentYIndex.set(currentYIndex);
    }

    public void setBoardDepth(int boardDepth) {
        this.boardDepth.set(boardDepth);
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth.set(boardWidth);
    }

    public void setRotation(int rotation) {
        this.rotation.set(rotation);
    }

    public int getBoardDepth() {
        return boardDepth.get();
    }

    public int getCurrentXIndex() {
        return currentXIndex.get();
    }

    public IntegerProperty currentXIndexProperty() {
        return currentXIndex;
    }

    public int getCurrentYIndex() {
        return currentYIndex.get();
    }

    public IntegerProperty currentYIndexProperty() {
        return currentYIndex;
    }

    public IntegerProperty boardDepthProperty() {
        return boardDepth;
    }

    public int getBoardWidth() {
        return boardWidth.get();
    }

    public boolean isRobotChanged() {
        return robotChanged.get();
    }

    public BooleanProperty robotChangedProperty() {
        return robotChanged;
    }

    public void setRobotChanged(boolean robotChanged) {
        this.robotChanged.set(robotChanged);
    }

    public IntegerProperty boardWidthProperty() {
        return boardWidth;
    }

    public int getRotation() {
        return rotation.get();
    }

    public IntegerProperty rotationProperty() {
        return rotation;
    }

    void updateCanvasSize(){
        setCanvasSize(mainCanvas, getWidth(), getHeight());
    }
    public RobotView(int boardWidth, int boardDepth) {
        this();
        createUI();
        this.boardDepth.set(boardDepth);
        this.boardWidth.set(boardWidth);

    }

    public void canvasChanged(boolean value){

        if(value) {
            var gc2 = drawingCanvas.getGraphicsContext2D();
            gc2.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
            var gc = mainCanvas.getGraphicsContext2D();
            gc.clearRect(20, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

            int scale = BOX_SCALE;
            int width = boardWidth.get();
            int depth = boardDepth.get();
            for(int j = 0; j < depth; ++j) {
                for(int i = 0; i < width; ++i){
                    int startX = i * scale;
                    int startY = j * scale;
                    gc.setFill(Color.rgb( (i * 50 + 50) % 255 , (j * 50 + 25) % 255, ((i + j + 25) * 50) % 255));
                    gc.fillRect(startX, startY, BOX_SCALE, BOX_SCALE);
                }
            }
        }

    }

    private int getContainingIndex(int y) {
        return Math.floorDiv(y, BOX_SCALE);
    }

    private Pane createUI() {
        mainCanvas.setOnMouseClicked(e->{
            pane.getChildren().clear();
            drawingCanvas.relocate(getContainingIndex((int) e.getX()) * BOX_SCALE, getContainingIndex((int) e.getY()) * BOX_SCALE);
            redrawLayouts();
            setCurrentXIndex(getContainingIndex((int) e.getX()) * BOX_SCALE);
            setCurrentYIndex(getContainingIndex((int) e.getY()) * BOX_SCALE);
        });
        drawingCanvas.setOnMouseClicked(e->{
            rotation.set((rotation.get() + 1)  % 4);

        });
        redrawLayouts();
        var gc2 = drawingCanvas.getGraphicsContext2D();
        gc2.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        gc2.drawImage(image, 15.0, 15.0, 70, 70);

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        this.setCenter(scrollPane);
        return this;
    }

    private void redrawLayouts() {
        pane.getChildren().add(mainCanvas);
        pane.getChildren().add(drawingCanvas);

    }

    private void drawRobot(int x, int y, int rotation) {
        pane.getChildren().clear();
        drawImage(x, y, rotation);
        LineTo lineTo = new LineTo(x * BOX_SCALE + 50, y * BOX_SCALE + 50);
        path.getElements().add(lineTo);
        redrawLayouts();
        prevX = x;
        prevY = y;

    }

    private void drawImage(int x, int y, int rotation) {
        var gc2 = drawingCanvas.getGraphicsContext2D();
        var gc1 = mainCanvas.getGraphicsContext2D();
        gc2.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        gc2.drawImage(image, 15.0, 15.0, 70, 70);
        gc1.setStroke(Color.rgb(0, 0, 0));
        LineTo lineTo = new LineTo(prevX * BOX_SCALE + 50, prevY * BOX_SCALE + 50);
        path.getElements().add(lineTo);
        gc1.lineTo(prevX * BOX_SCALE + 50, prevY * BOX_SCALE + 50);
        drawingCanvas.setRotate(rotation * 90);
    }

    public void startDrawing() {
        var gc1 = mainCanvas.getGraphicsContext2D();
        gc1.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        path = new Path();
        pathTransition = new PathTransition();
        pathTransition.setNode(drawingCanvas);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setDuration(Duration.millis(10000));
        MoveTo moveTo = new MoveTo(getCurrentXIndex(), getCurrentYIndex());
        path.getElements().add(moveTo);
        canvasChanged(true);
        gc1.beginPath();
    }
    public void endDrawing(){
        var gc1 = mainCanvas.getGraphicsContext2D();
        gc1.lineTo(getCurrentXIndex() * BOX_SCALE + 50, getCurrentYIndex() * BOX_SCALE + 50);
        gc1.stroke();
        LineTo lineTo = new LineTo(getCurrentXIndex() * BOX_SCALE, getCurrentYIndex() * BOX_SCALE);
        path.getElements().add(lineTo);
        pathTransition.play();
    }
}
