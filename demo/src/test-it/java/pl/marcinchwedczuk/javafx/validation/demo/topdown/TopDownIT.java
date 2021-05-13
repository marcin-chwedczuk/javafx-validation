package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.stage.Stage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import pl.marcinchwedczuk.javafx.validation.demo.BaseSequentialJavaFXTest;
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
        Thread.sleep(10000);
    }
}
