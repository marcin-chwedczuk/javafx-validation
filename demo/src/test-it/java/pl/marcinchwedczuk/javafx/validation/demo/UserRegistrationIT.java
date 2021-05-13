package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.scene.control.Alert;
import org.junit.jupiter.api.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.AlertInspector;
import pl.marcinchwedczuk.javafx.validation.demo.utils.StopOnFirstFailure;

@StopOnFirstFailure
public class UserRegistrationIT extends BaseJavaFXTest {
    private final UserRegistrationPageObject userRegistrationPO;

    public UserRegistrationIT(FxRobot robot) {
        this.userRegistrationPO = new UserRegistrationPageObject(robot);
    }

    @Start
    private void start(Stage stage) {
        userRegistrationPO.setupView(stage);
    }

    @Order(0)
    @Test
    void error_banner_not_visible_on_start() {
        userRegistrationPO.invalidBanner()
                .assertIsHidden();
    }

    @Order(10)
    @Test
    void user_enters_valid_data(FxRobot robot) throws InterruptedException {
        userRegistrationPO.username().setText("mike87");
        userRegistrationPO.password().setText("aBcD3fGh1@");

        // Trigger validation by moving focus from the password textfield
        userRegistrationPO.moveFocusToWindow();

        userRegistrationPO.invalidBanner()
                .assertIsHidden();

        userRegistrationPO.clickRegisterButton();

        new AlertInspector(robot, Alert.AlertType.INFORMATION)
                .assertAlertWindowVisible()
                .assertHasText("Registering user 'mike87' with password 'aBcD3fGh1@'!")
                .closeByClickingOK();
    }

    @Order(20)
    @Test
    void after_successful_registration_fields_should_be_cleared_and_error_banner_invisible() {
        userRegistrationPO.username()
                .assertEmpty();

        userRegistrationPO.password()
                .assertEmpty();

        userRegistrationPO.invalidBanner()
                .assertIsHidden();
    }

    @Order(30)
    @Test
    void leaving_empty_username_triggers_errors() {
        userRegistrationPO.username().setText("");
        userRegistrationPO.moveFocusToWindow();

        /*
        userRegistrationPO.invalidBanner()
                .assertIsVisible();
         */
    }
}