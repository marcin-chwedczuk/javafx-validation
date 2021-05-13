package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import javafx.css.Styleable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseButton;
import org.assertj.core.api.Condition;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotInterface;
import org.testfx.assertions.api.Assertions;

import java.util.Objects;

import static org.testfx.assertions.api.Assertions.assertThat;

public class TextFieldInspector {
    private final FxRobot robot;
    private final String selector;

    public TextFieldInspector(FxRobot robot, String selector) {
        this.robot = Objects.requireNonNull(robot);
        this.selector = Objects.requireNonNull(selector);
    }

    private TextField control() {
        return robot.lookup(selector).queryAs(TextField.class);
    }

    public TextFieldInspector setText(String text) {
        robot
                .clickOn(control(), MouseButton.PRIMARY)
                .write(text);

        return this;
    }

    public TextFieldInspector assertEmpty() {
        assertThat(control().getText())
            .as("TextField[" + selector + "] should be empty.")
            .isNullOrEmpty();
        return this;
    }
}
