package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Window;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.matcher.base.GeneralMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

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

        // Convert name to regex
        try {
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
        // TODO: Find OK Button
        robot.clickOn(robot.lookup(dialogSelector(".button")).queryButton());
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
