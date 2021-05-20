package pl.marcinchwedczuk.javafx.validation.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.TimeoutException;

public class DemoIT {
    private FxRobot robot = new FxRobot();
    private DemoPageObject demoPageObject = new DemoPageObject(robot);

    @BeforeEach
    public void setup() throws Exception {
        ApplicationTest.launch(App.class);
    }

    @AfterEach
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    public void application_starts_properly() {
        demoPageObject.userRegistrationPane()
                .assertNotEmpty();

        demoPageObject.numberRangePane()
                .assertNotEmpty();

        demoPageObject.topDownPane()
                .assertNotEmpty();
    }
}