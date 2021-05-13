package pl.marcinchwedczuk.javafx.validation.demo.range;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import pl.marcinchwedczuk.javafx.validation.demo.BaseSequentialJavaFXTest;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.AlertInspector;
import pl.marcinchwedczuk.javafx.validation.demo.registration.UserRegistrationPageObject;

public class NumberRangeIT extends BaseSequentialJavaFXTest {
    private final NumberRangePageObject numberRangePO;

    public NumberRangeIT(FxRobot robot) {
        this.numberRangePO = new NumberRangePageObject(robot);
    }

    @Start
    private void start(Stage stage) {
        numberRangePO.setupView(stage);
    }

    @Order(0)
    @Test
    void error_banner_not_visible_on_start() {
        numberRangePO.invalidBanner()
                .assertIsHidden();
    }

    @Order(10)
    @Test
    void user_enters_valid_data(FxRobot robot) {
        numberRangePO.from().setText("1");
        numberRangePO.to().setText("20");

        // Trigger validation by moving focus from the password textfield
        numberRangePO.moveFocusToWindow();

        numberRangePO.clickValidateButton();

        new AlertInspector(robot, Alert.AlertType.INFORMATION)
                .assertAlertWindowVisible()
                .assertHasText("Valid range 1 - 20.")
                .closeByClickingOK();
    }
}
