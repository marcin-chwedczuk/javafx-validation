package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.marcinchwedczuk.javafx.validation.demo.mainwindow.Demo;

/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) {
        Demo.showOn(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}