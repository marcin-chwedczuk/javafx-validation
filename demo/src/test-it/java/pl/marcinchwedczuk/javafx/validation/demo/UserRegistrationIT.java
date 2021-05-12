package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.scene.control.Alert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.AlertInspector;

import java.util.concurrent.ThreadLocalRandom;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    void error_banner_not_visible_at_start() {
        userRegistrationPO.invalidBanner().assertNotVisible();
    }

    @Order(10)
    @Test
    void user_enters_valid_data(FxRobot robot) throws InterruptedException {
        userRegistrationPO.username().setText("mike87");
        userRegistrationPO.password().setText("aBcD3fGh1@");
        userRegistrationPO.moveFocusToWindow();

        userRegistrationPO.invalidBanner().assertNotVisible();

        userRegistrationPO.clickRegisterButton();

        new AlertInspector(robot, Alert.AlertType.INFORMATION)
                .assertAlertWindowVisible()
                .assertHasText("Registering user 'mike87' with password 'aBcD3fGh1@'!")
                .closeByClickingOK();
    }

    @Order(20)
    @Test
    void after_sucessfull_registration_fields_should_be_cleared() {

    }
}