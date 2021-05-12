package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import org.testfx.api.FxRobot;

import java.util.Objects;

public class ButtonInspector {
    private final FxRobot robot;
    private final String selector;

    public ButtonInspector(FxRobot robot, String selector) {
        this.robot = Objects.requireNonNull(robot);
        this.selector = Objects.requireNonNull(selector);
    }

    public ButtonInspector click() {
        robot.clickOn(selector);
        return this;
    }
}
