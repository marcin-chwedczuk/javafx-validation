package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import javafx.scene.input.MouseButton;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotInterface;

import java.util.Objects;

public class TextFieldInspector {
    private final FxRobot robot;
    private final String selector;

    public TextFieldInspector(FxRobot robot, String selector) {
        this.robot = Objects.requireNonNull(robot);
        this.selector = Objects.requireNonNull(selector);
    }

    private FxRobotInterface focusControl() {
        return robot.clickOn(selector, MouseButton.PRIMARY);
    }

    public TextFieldInspector setText(String text) {
        focusControl().write(text);
        return this;
    }
}
