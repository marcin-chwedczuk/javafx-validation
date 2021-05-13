package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import org.testfx.api.FxRobot;

import java.util.Objects;

public class ButtonInspector extends BaseControlInspector {
    public ButtonInspector(FxRobot robot, String fxid) {
        super(robot, fxid);
    }

    public ButtonInspector click() {
        robot.clickOn(idSelector());
        return this;
    }
}
