package pl.marcinchwedczuk.javafx.validation;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.marcinchwedczuk.javafx.validation.demo.Demo;

/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) {
        Demo.show();
    }

    public static void main(String[] args) {
        launch();
    }
}