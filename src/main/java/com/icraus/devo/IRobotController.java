package com.icraus.devo;

@FunctionalInterface
public interface IRobotController {
    Pose executeRoute(Pose pose, String route) throws UnSupportedMoveOperationException;
}
