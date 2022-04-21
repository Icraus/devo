package com.icraus.devo.robot;


import com.icraus.devo.IRobotController;
import com.icraus.devo.Pose;
import com.icraus.devo.RobotController;
import com.icraus.devo.UnSupportedMoveOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RobotControllerTest {
    IRobotController createRobotController(int width, int depth){
        return new RobotController(width, depth);
    }
    @Test
    void testRobotControllerReport() throws UnSupportedMoveOperationException {
        int deep = 5, width = 5;
        String route = "RFRFFRFRF";
        Pose pose = new Pose(1, 2, 'N');
        Pose expectedPose = new Pose(1, 3, 'N');
        IRobotController controller = createRobotController(deep, width);
        Pose resultPoint = controller.executeRoute(pose, route);
        Assertions.assertEquals(expectedPose, resultPoint);

        controller = new RobotController(5, 5);
        pose = new Pose(0, 0, 'E');
        route = "RFLFFLRF";
        expectedPose = new Pose(3, 1, 'E');
        resultPoint = controller.executeRoute(pose, route);
        Assertions.assertEquals(expectedPose, resultPoint);

    }

    @Test
    void testRobotControllerReportThrowsExceptionInvalidRoute() {
        int deep = 5, width = 5;
        String route = "RFRFFRFRFCD";
        Pose pose = new Pose(1, 2, 'N');
        IRobotController controller = createRobotController(deep, width);
        Assertions.assertThrows(UnSupportedMoveOperationException.class, ()->{
            Pose resultPoint = controller.executeRoute(pose, route);
        });
    }

    @Test
    void testRobotControllerReportThrowsExceptionInvalidShape() {
        int deep = 2, width = 2;
        String route = "RFRFFRFRF";
        Pose pose = new Pose(4, 4, 'N');
        IRobotController controller = new RobotController(deep, width);
        Assertions.assertThrows(UnSupportedMoveOperationException.class, ()->{
            Pose resultPoint = controller.executeRoute(pose, route);
        });
    }
}
