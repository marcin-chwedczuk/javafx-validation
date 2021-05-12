package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.scene.control.Alert;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;


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
                .assertAlertWindowVisible()
                .assertHasText("Registering user 'mike87' with password 'aBcD3fGh1@'!")
                .closeByClickingOK();
    }
}