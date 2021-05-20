package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class AppIT extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        new App().start(stage);
    }

    @Test
    public void application_starts_properly(FxRobot robot) {
    }
}