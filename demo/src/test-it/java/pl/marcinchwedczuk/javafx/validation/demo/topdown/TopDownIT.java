package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import pl.marcinchwedczuk.javafx.validation.testutils.BaseSequentialJavaFXTest;
import pl.marcinchwedczuk.javafx.validation.testutils.inspectors.AlertInspector;

public class TopDownIT extends BaseSequentialJavaFXTest {
    private static final String PHONE_REQUIRED_MSG = "Phone number is required.";
    private static final String FAX_REQUIRED_MSG = "Fax number is required.";
    private static final String WRONG_COUNTRY_CODE_MSG = "Wrong country code prefix.";

    private final TopDownPageObject topDownPO;

    public TopDownIT(FxRobot robot) {
        this.topDownPO = new TopDownPageObject(robot);
    }

    @Start
    private void start(Stage stage) {
        topDownPO.setupView(stage);
    }

    @Order(0)
    @Test
    void error_banner_not_visible_on_start() throws InterruptedException {
        topDownPO.invalidBanner()
                .assertHidden();
    }

    @Order(10)
    @Test
    void entering_valid_data_results_in_ok_alert(FxRobot robot) {
        topDownPO.country()
                .selectValue(Country.POLAND);

        topDownPO.mobile()
                .setText("+48 666 777 888");

        topDownPO.fax()
                .setText("+48 111 222 333");

        topDownPO.clickValidateButton();

        new AlertInspector(robot, Alert.AlertType.INFORMATION)
                .assertAlertWindowVisible()
                .assertHasText(
                        "Mobile Phone: +48 666 777 888\n" +
                                "Fax: +48 111 222 333\n" +
                                "Country: POLAND")
                .closeByClickingOK();
    }

    @Order(20)
    @Test
    void after_clicking_validate_fields_are_empty() {
        topDownPO.country()
                .assertNoValueSelected();

        topDownPO.mobile()
                .assertEmpty();

        topDownPO.fax()
                .assertEmpty();
    }

    @Order(30)
    @Test
    void leaving_fields_empty_triggers_simple_validation() {
        topDownPO.mobile().setText("");
        topDownPO.fax().setText("");
        topDownPO.moveFocusToWindow();

        topDownPO.mobileErrors()
                .assertShowsError(PHONE_REQUIRED_MSG);

        topDownPO.faxErrors()
                .assertShowsError(FAX_REQUIRED_MSG);
    }

    @Order(40)
    @Test
    void pushing_validate_buttons_shows_invalid_banner() {
        topDownPO.clickValidateButton();

        topDownPO.invalidBanner()
                .assertVisible();
    }

    @Order(50)
    @Test
    void after_selecting_country_there_are_still_only_required_errors() {
        topDownPO.country()
                .selectValue(Country.RUSSIA);

        topDownPO.mobileErrors()
                .assertShowsError(PHONE_REQUIRED_MSG)
                .assertDoesNotShowError(WRONG_COUNTRY_CODE_MSG);

        topDownPO.faxErrors()
                .assertShowsError(FAX_REQUIRED_MSG)
                .assertDoesNotShowError(WRONG_COUNTRY_CODE_MSG);
    }

    @Order(60)
    @Test
    void after_putting_wrong_numbers_now_there_are_wrong_country_code_errors() {
        topDownPO.mobile().setText("123");
        topDownPO.fax().setText("123");
        topDownPO.moveFocusToWindow();

        topDownPO.mobileErrors()
                .assertShowsError(WRONG_COUNTRY_CODE_MSG)
                .assertDoesNotShowError(PHONE_REQUIRED_MSG);

        topDownPO.faxErrors()
                .assertShowsError(WRONG_COUNTRY_CODE_MSG)
                .assertDoesNotShowError(FAX_REQUIRED_MSG);
    }

    @Order(70)
    @Test
    void after_putting_right_numbers_errors_disappear() {
        topDownPO.mobile().setText("+7 123");
        topDownPO.fax().setText("+7 123");
        topDownPO.moveFocusToWindow();

        topDownPO.mobileErrors()
                .assertNoErrorVisible();
        topDownPO.faxErrors()
                .assertNoErrorVisible();
    }
}
