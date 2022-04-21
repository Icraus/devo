package com.icraus.devo;

@FunctionalInterface
public interface ICommander {
    void command(Pose pose) throws UnSupportedMoveOperationException;

}
