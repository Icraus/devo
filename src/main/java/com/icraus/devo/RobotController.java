package com.icraus.devo;

public class RobotController {
    private int depth;
    private int width;
    private Pose currentPose;

    public RobotController(int depth, int width) {
        this.depth = depth;
        this.width = width;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public Pose executeRoute(Pose pose, String route) throws UnSupportedMoveOperation {
        setCurrentPose(pose);
        for(char currentMove : route.toCharArray()){
            switch (currentMove){
                case 'L':
                    turnLeft();
                    break;
                case 'R':
                    turnRight();
                    break;
                case 'F':
                    moveForward();
                    break;
                default:
                    throw new UnSupportedMoveOperation("Error, "+ currentMove+ " is Not supported");
            }
        }
        return getCurrentPose();
    }

    protected void turnRight() {
        switch (getCurrentPose().getOrient()){
            case 'W':
                getCurrentPose().setOrient('N');
                break;
            case 'E':
                getCurrentPose().setOrient('S');
                break;
            case 'N':
                getCurrentPose().setOrient('E');
                break;
            case 'S':
                getCurrentPose().setOrient('W');
        }
    }

    protected void turnLeft() {
        switch (getCurrentPose().getOrient()){
            case 'W':
                getCurrentPose().setOrient('S');
                break;
            case 'E':
                getCurrentPose().setOrient('N');
                break;
            case 'N':
                getCurrentPose().setOrient('W');
                break;
            case 'S':
                getCurrentPose().setOrient('E');
        }
    }
    protected void moveForward() throws UnSupportedMoveOperation {
        var x = getCurrentPose().getX();
        var y = getCurrentPose().getY();
        switch (getCurrentPose().getOrient()){
            case 'W':
                getCurrentPose().setX(x - 1);
                if(getCurrentPose().getX() >= getWidth())
                    throw new UnSupportedMoveOperation("Error, Ilegal Move");
                break;
            case 'E':
                getCurrentPose().setX(x + 1);
                if(getCurrentPose().getX() >= getWidth())
                    throw new UnSupportedMoveOperation("Error, Ilegal Move");
                break;
            case 'N':
                getCurrentPose().setY(y - 1);
                if(getCurrentPose().getY() >= getDepth())
                    throw new UnSupportedMoveOperation("Error, Ilegal Move");
                break;
            case 'S':
                if(getCurrentPose().getY() >= getDepth())
                    throw new UnSupportedMoveOperation("Error, Ilegal Move");
                getCurrentPose().setY(y + 1);
        }
    }

    public Pose getCurrentPose() {
        return currentPose;
    }

    public void setCurrentPose(Pose currentPose) {
        this.currentPose = currentPose;
    }
}
