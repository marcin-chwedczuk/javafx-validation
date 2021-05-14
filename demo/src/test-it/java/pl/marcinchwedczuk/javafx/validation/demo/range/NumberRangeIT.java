package pl.marcinchwedczuk.javafx.validation.demo.range;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import pl.marcinchwedczuk.javafx.validation.demo.BaseSequentialJavaFXTest;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.AlertInspector;

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
                .assertHidden();
    }

    @Order(10)
    @Test
    void when_user_enters_valid_data_we_show_alert(FxRobot robot) {
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

    @Order(20)
    @Test
    void after_pressing_validation_button_fields_are_cleared() {
        numberRangePO.from()
                .assertEmpty();

        numberRangePO.to()
                .assertEmpty();

        numberRangePO.invalidBanner()
                .assertHidden();
    }

    @Order(30)
    @Test
    void validation_errors_are_shown_after_leaving_fields_empty() {
        numberRangePO.to().setText("");
        numberRangePO.from().setText("");

        numberRangePO.moveFocusToWindow();

        numberRangePO.toErrors()
                .assertShowsError("Value is required.");

        numberRangePO.fromErrors()
                .assertShowsError("Value is required.");
    }

    @Order(40)
    @Test
    void error_shows_when_user_enters_invalid_range() {
        numberRangePO.from().setText("10");
        numberRangePO.to().setText("1");
        numberRangePO.moveFocusToWindow();

        numberRangePO.toErrors()
                .assertShowsError("Invalid range of numbers: 1 (this number) must be greater than 10.");
    }

    @Order(50)
    @Test
    void fixing_range_by_editing_to_field_hides_error() {
        numberRangePO.to().setText("11");

        numberRangePO.toErrors()
                .assertNoErrorVisible();
    }

    @Order(60)
    @Test
    void making_range_invalid_by_editing_from_field_shows_error() {
        numberRangePO.from().setText("20");

        numberRangePO.toErrors()
                .assertShowsError("Invalid range of numbers: 11 (this number) must be greater than 20.");
    }

    @Order(70)
    @Test
    void clicking_button_when_range_is_invalid_shows_error_banner() {
        numberRangePO.clickValidateButton();

        numberRangePO.invalidBanner()
                .assertVisible()
                .assertHasText("Invalid data...");
    }
}
