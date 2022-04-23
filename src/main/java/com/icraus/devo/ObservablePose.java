package com.icraus.devo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Map;

public class ObservablePose extends Pose{
    private Hook mHook;

    public void setHook(Hook mHook) {
        this.mHook = mHook;
    }

    @FunctionalInterface
    public static interface Hook{
        void run();
    }
    private static final Map<Integer, Character> orientMap =  Map.of(
            0, RobotController.NORTH,
            1, RobotController.EAST,
            2, RobotController.SOUTH,
            3, RobotController.WEST
    );
    private static Map<Character, Integer> inverseOrientMap = Map.of(
            RobotController.NORTH, 0,
            RobotController.EAST, 1,
            RobotController.SOUTH, 2,
            RobotController.WEST, 3
    );

    private IntegerProperty mX = new SimpleIntegerProperty();
    private IntegerProperty mY = new SimpleIntegerProperty();
    private IntegerProperty rotation = new SimpleIntegerProperty();
    public ObservablePose() {
        super(0, 0, 'N');
        if(rotation != null){
            rotation.addListener(e -> {
                char orient = orientMap.get(rotation.get());
                setOrient(orient);
            });
        }
    }
    public void hook(){
        getHook().run();
    }

    private Hook getHook() {
        return mHook;
    }

    @Override
    public int getX() {
        return mX.get();
    }

    public IntegerProperty xProperty() {
        return mX;
    }

    @Override
    public void setX(int x) {
        if(mX != null){
            super.setX(x);
            this.mX.set(x);
            hook();
        }

    }

    @Override
    public int getY() {
        return mY.get();
    }

    public IntegerProperty yProperty() {
        return mY;
    }

    @Override
    public void setY(int y) {
        if(mY != null) {
            super.setY(y);
            this.mY.set(y);
            hook();
        }
    }

    public int getRotation() {
        return rotation.get();
    }

    public IntegerProperty rotationProperty() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation.set(rotation);
        hook();
    }

    @Override
    public void setOrient(char orient) {
        if(rotation != null) {
            super.setOrient(orient);
            Integer rotation = inverseOrientMap.get(orient);
            this.rotation.set(rotation);
        }
    }
}
