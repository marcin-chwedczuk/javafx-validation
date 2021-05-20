package pl.marcinchwedczuk.javafx.validation.testutils.inspectors;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import org.testfx.api.FxRobot;

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
