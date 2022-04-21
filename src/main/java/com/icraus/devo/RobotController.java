package com.icraus.devo;

public class RobotController implements IRobotController {
    public static final char NORTH = 'N';
    public static final char SOUTH = 'S';
    public static final char WEST = 'W';
    public static final char EAST = 'E';
    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';
    public static final char FORWARD = 'F';
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


    @Override
    public Pose executeRoute(Pose pose, String route) throws UnSupportedMoveOperationException {
        setCurrentPose(pose);
        for(char currentMove : route.toCharArray()){
            switch (currentMove){
                case LEFT:
                    turnLeft();
                    break;
                case RIGHT:
                    turnRight();
                    break;
                case FORWARD:
                    moveForward();
                    break;
                default:
                    throw new UnSupportedMoveOperationException("Error, "+ currentMove+ " is Not supported");
            }
        }
        return getCurrentPose();
    }


    @Override
    public void turnRight() throws UnSupportedMoveOperationException {
        navigateRobot(NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public void turnLeft() throws UnSupportedMoveOperationException {
        navigateRobot(SOUTH, NORTH, WEST, EAST);
    }

    @Override
    public void moveForward() throws UnSupportedMoveOperationException {
        var x = getCurrentPose().getX();
        var y = getCurrentPose().getY();
        switch (getCurrentPose().getOrient()){
            case WEST:
                getCurrentPose().setX(x - 1);
                break;
            case EAST:
                getCurrentPose().setX(x + 1);
                break;
            case NORTH:
                getCurrentPose().setY(y - 1);
                break;
            case SOUTH:
                getCurrentPose().setY(y + 1);
        }
        validateNavigation();

    }

    private void validateNavigation() throws UnSupportedMoveOperationException {
        if (getCurrentPose().getX() >= width || getCurrentPose().getY() >= depth)
            throw new UnSupportedMoveOperationException("Error, Ilegal Move");
    }

    //Cool refactor isn't it :D
    private void navigateRobot(char north, char south, char east, char west) {
        switch (getCurrentPose().getOrient()){
            case WEST:
                getCurrentPose().setOrient(north);
                break;
            case EAST:
                getCurrentPose().setOrient(south);
                break;
            case NORTH:
                getCurrentPose().setOrient(east);
                break;
            case SOUTH:
                getCurrentPose().setOrient(west);
        }
    }

    public Pose getCurrentPose() {
        return currentPose;
    }

    public void setCurrentPose(Pose currentPose) {
        this.currentPose = currentPose;
    }
}
