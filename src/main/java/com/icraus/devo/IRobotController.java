package com.icraus.devo;

public interface IRobotController {
    Pose executeRoute(Pose pose, String route) throws UnSupportedMoveOperationException;

    void turnRight() throws UnSupportedMoveOperationException;

    void turnLeft() throws UnSupportedMoveOperationException;

    void moveForward() throws UnSupportedMoveOperationException;
}
