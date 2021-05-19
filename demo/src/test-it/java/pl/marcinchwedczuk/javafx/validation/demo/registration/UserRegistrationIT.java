package pl.marcinchwedczuk.javafx.validation.demo.registration;

import javafx.scene.control.Alert;
import org.junit.jupiter.api.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;
import pl.marcinchwedczuk.javafx.validation.testutils.BaseSequentialJavaFXTest;
import pl.marcinchwedczuk.javafx.validation.testutils.inspectors.AlertInspector;

public class UserRegistrationIT extends BaseSequentialJavaFXTest {
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
                .assertHidden();
    }

    @Order(10)
    @Test
    void user_enters_valid_data(FxRobot robot) {
        userRegistrationPO.username().setText("mike87");
        userRegistrationPO.password().setText("aBcD3fGh1@");

        // Trigger validation by moving focus from the password textfield
        userRegistrationPO.moveFocusToWindow();

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
                .assertHidden();
    }

    @Order(30)
    @Test
    void leaving_empty_username_triggers_errors() {
        userRegistrationPO.username().setText("");
        userRegistrationPO.moveFocusToWindow();

        userRegistrationPO.usernameErrors()
            .assertShowsError("Value is required.")
            .assertShowsError("Value must be at least 2 and at most 15 characters long.")
            .assertShowsError("Username can only consists of underscore, lower-case letters and digits and cannot start with a digit.");
    }

    @Order(40)
    @Test
    void leaving_empty_password_triggers_errors() {
        userRegistrationPO.password().setText("");
        userRegistrationPO.moveFocusToWindow();

        userRegistrationPO.passwordErrors()
                .assertShowsError("Value is required.")
                .assertShowsError("Password must have at least 8 characters.")
                .assertShowsError("Password must contain an upper-case letter.")
                .assertShowsError("Password must contain a lower-case letter.")
                .assertShowsError("Password must contain a digit.")
                .assertShowsError("Password must contain a special character.");
    }

    @Order(50)
    @Test
    void clicking_register_with_invalid_data_shows_error_banner() {
        userRegistrationPO.clickRegisterButton();

        userRegistrationPO.invalidBanner()
                .assertVisible()
                .assertHasText("Invalid data...");
    }

    @Order(50)
    @Test
    void entering_valid_data_hides_errors() {
        userRegistrationPO.username().setText("mike87");
        userRegistrationPO.password().setText("aBcD3fGh1@");

        userRegistrationPO.usernameErrors()
                .assertNoErrorVisible();

        userRegistrationPO.passwordErrors()
                .assertNoErrorVisible();
    }
}