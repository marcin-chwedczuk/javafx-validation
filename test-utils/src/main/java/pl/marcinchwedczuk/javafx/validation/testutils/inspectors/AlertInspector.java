package pl.marcinchwedczuk.javafx.validation.testutils.inspectors;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.testfx.api.FxRobot;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.fail;
import static org.testfx.assertions.api.Assertions.assertThat;

public class AlertInspector {
    private final FxRobot robot;
    private Alert.AlertType type;

    public AlertInspector(FxRobot robot, Alert.AlertType type) {
        this.robot = Objects.requireNonNull(robot);
        this.type = Objects.requireNonNull(type);
    }

    public AlertInspector assertAlertWindowVisible() {
        String windowTitle =
                type == Alert.AlertType.INFORMATION ? "Information" :
                type == Alert.AlertType.WARNING ? "Warning" :
                type == Alert.AlertType.ERROR ? "Error" :
                "";

        try {
            // Convert name to regex
            robot.window("^" + Pattern.quote(windowTitle) + "$");
        } catch (NoSuchElementException e) {
            org.assertj.core.api.Assertions.fail("Alert window not visible " +
                    "(cannot find window with title: " + windowTitle + ").");
        }

        return this;
    }

    public AlertInspector assertHasText(String text) {
        assertThat(getDialogContent())
                .as("Alert dialog box content")
                .isEqualTo(text);

        return this;
    }

    public AlertInspector closeByClickingOK() {
        Button okButton = findButtonWithText("Ok");
        robot.clickOn(okButton);

        return this;
    }

    private String getDialogContent() {
        return robot.lookup(dialogSelector(".content"))
                .queryLabeled()
                .getText();
    }

    private Button findButtonWithText(String text) {
        Set<Button> buttons = robot
                .lookup(dialogSelector(".button"))
                .queryAllAs(Button.class);

        Set<Button> matchingButtons = buttons.stream()
                .filter(b -> text.equalsIgnoreCase(b.getText()))
                .collect(toSet());

        if (matchingButtons.size() > 1) {
            fail("Found more than one button matching '%s' on dialog box.", text);
        }
        else if (matchingButtons.isEmpty()) {
            fail("No button matching text '%s' found on dialog box.", text);
        }

        return matchingButtons.iterator().next();
    }

    private String dialogSelector(String selector) {
        return String.format("%s %s", dialogId(), selector);
    }

    private String dialogId() {
        return
                type == Alert.AlertType.INFORMATION ? "#info-dialog" :
                type == Alert.AlertType.WARNING ? "#warning-dialog" :
                "";
    }
}
