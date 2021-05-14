package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import pl.marcinchwedczuk.javafx.validation.demo.BaseSequentialJavaFXTest;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.AlertInspector;
import pl.marcinchwedczuk.javafx.validation.demo.range.NumberRangePageObject;

public class TopDownIT extends BaseSequentialJavaFXTest {
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
    void works(FxRobot robot) {
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
}
