package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.scene.control.Alert;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.stage.Stage;
import org.testfx.util.DebugUtils;


public class UserRegistrationIT extends BaseJavaFXTest {
    private final UserRegistrationPageObject userRegistrationPO;

    public UserRegistrationIT(FxRobot robot) {
        this.userRegistrationPO = new UserRegistrationPageObject(robot);
    }

    @Start
    private void start(Stage stage) {
        userRegistrationPO.setupView(stage);
    }

    @Test
    void should_contain_button_with_text(FxRobot robot) throws InterruptedException {
        userRegistrationPO.username().setText("mike87");
        userRegistrationPO.password().setText("aBcD3fGh1@");
        userRegistrationPO.clickRegisterButton();

        new AlertInspector(robot, Alert.AlertType.INFORMATION)
                .assertContainsText("XRegistering user");

                robot.clickOn("OK");
    }
}