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
import org.testfx.matcher.base.GeneralMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.Objects;
import java.util.regex.Pattern;

public class AlertInspector {
    private final FxRobot robot;
    private Alert.AlertType type;

    public AlertInspector(FxRobot robot, Alert.AlertType type) {
        this.robot = Objects.requireNonNull(robot);
        this.type = Objects.requireNonNull(type);
    }

    private Scene alertWindow() {
        String windowTitle =
                type == Alert.AlertType.INFORMATION ? "Information" :
                type == Alert.AlertType.WARNING ? "Warning" :
                type == Alert.AlertType.ERROR ? "Error" :
                "";

        // Convert name to regex
        Window alertWindow = robot.window("^" + Pattern.quote(windowTitle) + "$");
        if (alertWindow == null) throw new RuntimeException("Dialog window is not visible");
        return alertWindow.getScene();
    }

    public AlertInspector assertContainsText(String text) {
        Label l = (Label)alertWindow().lookup("#info-dialog .content");
        FxAssert.verifyThat(l, LabeledMatchers.hasText(new BaseMatcher<String>() {
            @Override
            public boolean matches(Object item) {
                return ((String)item).contains(text);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("contains '" + text + "'");
            }
        }));
        return this;
    }
}
