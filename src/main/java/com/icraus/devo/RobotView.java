package com.icraus.devo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class RobotView extends BorderPane {
    private static final int BOX_SCALE = 100;
    private final IntegerProperty currentXIndex = new SimpleIntegerProperty();
    private final IntegerProperty currentYIndex = new SimpleIntegerProperty();
    private final IntegerProperty boardDepth = new SimpleIntegerProperty(5);
    private final IntegerProperty boardWidth = new SimpleIntegerProperty(5);
    private final IntegerProperty rotation = new SimpleIntegerProperty(0);

    final Canvas mainCanvas = new Canvas(500,500);
    final Canvas drawingCanvas = new Canvas(500,500);

    Image image = new Image("/robot.png");
    private RobotView(){
        widthProperty().addListener(e ->{
            updateCanvasSize();
        });
        heightProperty().addListener(e->{
            updateCanvasSize();
        });
        currentXIndex.addListener(e -> {
            rotation.set(0);
        });
        currentYIndex.addListener(e -> {
            rotation.set(0);
        });
        boardWidthProperty().addListener(e -> {
            canvasChanged(true);
        });
        boardDepthProperty().addListener(e -> {
            canvasChanged(true);
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

    public IntegerProperty boardDepthProperty() {
        return boardDepth;
    }

    public int getBoardWidth() {
        return boardWidth.get();
    }

    public IntegerProperty boardWidthProperty() {
        return boardWidth;
    }

    void updateCanvasSize(){
        setCanvasSize(mainCanvas, getWidth(), getHeight());
        setCanvasSize(drawingCanvas, getWidth(), getHeight());
    }
    public RobotView(int boardWidth, int boardDepth) {
        this();
        createUI();
        this.boardDepth.set(boardDepth);
        this.boardWidth.set(boardWidth);

    }

    public void canvasChanged(boolean value){

        if(value) {
            var gc = mainCanvas.getGraphicsContext2D();
            var gc2 = drawingCanvas.getGraphicsContext2D();
            gc2.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
            gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

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
        drawingCanvas.setOnMouseClicked(e->{
            currentXIndex.set(getContainingIndex((int) e.getX()));
            currentYIndex.set(getContainingIndex((int) e.getY()));
            drawRobot(currentXIndex.get(), currentYIndex.get(), rotation.get());
            rotation.set((rotation.get() + 1)  % 4);
        });

        ScrollPane scrollPane = new ScrollPane();
        Pane pane = new Pane();
        pane.getChildren().add(mainCanvas);
        pane.getChildren().add(drawingCanvas);
        scrollPane.setContent(pane);
        scrollPane.setFitToWidth(true);
        this.setCenter(scrollPane);
        return this;
    }

    private void drawRobot(int x, int y, int rotation) {
        var gc2 = drawingCanvas.getGraphicsContext2D();
        gc2.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        gc2.save();
        Rotate r = new Rotate(rotation * 90, x * BOX_SCALE + 50, y * BOX_SCALE + 50);
        gc2.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        gc2.drawImage(image, x * BOX_SCALE + 15.0, y * BOX_SCALE + 15.0, 70, 70);
        gc2.restore();
    }
}
