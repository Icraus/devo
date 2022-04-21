package com.icraus.devo;

import java.util.HashMap;
import java.util.Map;

/**
 * I KNOW IT MIGHT BE OVERKILL TO USE THESE DESIGN PATTERN,
 * I JUST WANTED TO SHOW OFF MY SKILLS.
 * IF U NEED THE KEEP IT SIMPLE STUPID SOLUTION YOU WILL FIND IT ON THE PREVIOUS COMMIT
 */
public class RobotController implements IRobotController {
    public static final char NORTH = 'N';
    public static final char SOUTH = 'S';
    public static final char WEST = 'W';
    public static final char EAST = 'E';
    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';
    public static final char FORWARD = 'F';
    private final static Map<Character, ICommander> commanderMap = new HashMap<>();
    static {
        commanderMap.put(RIGHT, RobotController::turnRight);
        commanderMap.put(LEFT, RobotController::turnLeft);
        commanderMap.put(FORWARD, RobotController::moveForward);
    }

    public static Map<Character, ICommander> getCommanderMap() {
        return commanderMap;
    }

    private int depth;
    private int width;

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
        for(char currentMove : route.toCharArray()){
            if(!commanderMap.containsKey(currentMove)){
                throw new UnSupportedMoveOperationException("Error, "+ currentMove + " is Not supported");
            }
            commanderMap.get(currentMove).command(pose);
            validateNavigation(pose);
        }
        return pose;
    }

    private void validateNavigation(Pose pose) throws UnSupportedMoveOperationException {
        if (pose.getX() >= width || pose.getY() >= depth)
            throw new UnSupportedMoveOperationException("Error, Illegal Move");
    }

    private static void turnRight(Pose pose) throws UnSupportedMoveOperationException {
        navigateRobot(pose, NORTH, SOUTH, EAST, WEST);
    }

    private static void turnLeft(Pose pose) throws UnSupportedMoveOperationException {
        navigateRobot(pose, SOUTH, NORTH, WEST, EAST);
    }

    private static void moveForward(Pose pose) throws UnSupportedMoveOperationException {
        var x = pose.getX();
        var y = pose.getY();
        switch (pose.getOrient()){
            case WEST:
                pose.setX(x - 1);
                break;
            case EAST:
                pose.setX(x + 1);
                break;
            case NORTH:
                pose.setY(y - 1);
                break;
            case SOUTH:
                pose.setY(y + 1);
        }
    }


    //Cool refactor isn't it :D
    private static void navigateRobot(Pose pose, char westMove, char eastMove, char northMove, char southMove) {
        switch (pose.getOrient()){
            case WEST:
                pose.setOrient(westMove);
                break;
            case EAST:
                pose.setOrient(eastMove);
                break;
            case NORTH:
                pose.setOrient(northMove);
                break;
            case SOUTH:
                pose.setOrient(southMove);
        }
    }
}
