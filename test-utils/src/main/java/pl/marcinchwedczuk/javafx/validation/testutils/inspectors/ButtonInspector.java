package pl.marcinchwedczuk.javafx.validation.testutils.inspectors;

import org.testfx.api.FxRobot;

public class ButtonInspector extends BaseControlInspector {
    public ButtonInspector(FxRobot robot, String fxid) {
        super(robot, fxid);
    }

    public ButtonInspector click() {
        robot.clickOn(idSelector());
        return this;
    }
}
