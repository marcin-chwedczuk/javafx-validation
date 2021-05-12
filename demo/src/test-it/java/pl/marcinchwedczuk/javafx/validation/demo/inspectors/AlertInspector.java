package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.testfx.api.FxRobot;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
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
        assertThat(robot.lookup(dialogSelector(".content")).queryLabeled())
                .hasText(text);
        return this;
    }

    public void closeByClickingOK() {
        Set<Button> buttons = robot.lookup(dialogSelector(".button")).queryAll();
        System.out.println("Found buttons: " + buttons.size());

        Set<Button> okButtons = buttons.stream()
                .filter(b -> "ok".equalsIgnoreCase(b.getText()))
                .collect(toSet());

        assertThat(okButtons.size())
                .as("number of ok buttons")
                .isEqualTo(1);

        // Click on the only button
        robot.clickOn(okButtons.iterator().next());
    }

    private String dialogSelector(String selector) {
        return dialogId() + " " + selector;
    }

    private String dialogId() {
        return
                type == Alert.AlertType.INFORMATION ? "#info-dialog" :
                type == Alert.AlertType.WARNING ? "#warning-dialog" :
                "";
    }
}
