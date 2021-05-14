package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import javafx.application.Platform;
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
import org.testfx.util.WaitForAsyncUtils;

import java.util.Objects;

import static org.testfx.assertions.api.Assertions.assertThat;

public class TextFieldInspector extends BaseControlInspector {
    public TextFieldInspector(FxRobot robot, String fxid) {
        super(robot, fxid);
    }

    private TextField control() {
        return robot
                .lookup(idSelector())
                .queryAs(TextField.class);
    }

    public TextFieldInspector setText(String text) {
        TextField control = control();

        robot
            .interact(() -> control.clear())
            .clickOn(control, MouseButton.PRIMARY)
            .write(text);

        return this;
    }

    public TextFieldInspector assertEmpty() {
        assertThat(control().getText())
            .as("TextField[#" + fxid + "] should be empty.")
            .isNullOrEmpty();

        return this;
    }
}
