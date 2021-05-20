package pl.marcinchwedczuk.javafx.validation.demo.mainwindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import pl.marcinchwedczuk.javafx.validation.demo.controls.DebouncingEventHandler;
import pl.marcinchwedczuk.javafx.validation.demo.controls.LayoutAwareVBox;
import pl.marcinchwedczuk.javafx.validation.demo.controls.LayoutEvent;
import pl.marcinchwedczuk.javafx.validation.demo.range.NumberRange;
import pl.marcinchwedczuk.javafx.validation.demo.registration.UserRegistration;
import pl.marcinchwedczuk.javafx.validation.demo.topdown.TopDownView;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

public class Demo implements Initializable {
    public static Demo showOn(Stage window) {
        try {
            FXMLLoader loader = new FXMLLoader(Demo.class.getResource("Demo.fxml"));

            Scene scene = new Scene(loader.load());
            Demo controller = (Demo) loader.getController();

            window.setTitle("Validation Demo");
            window.setScene(scene);
            window.setResizable(false);

            DebouncingEventHandler<LayoutEvent> resizeWindowEventHandler =
                    new DebouncingEventHandler<>(Duration.ofMillis(1), event -> {
                        System.out.println("RESIZE");
                        window.sizeToScene();
                    });
            LayoutAwareVBox root = (LayoutAwareVBox) scene.getRoot();
            root.addEventHandler(LayoutEvent.LAYOUT_EVENT_TYPE, resizeWindowEventHandler);

            window.show();

            return controller;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private TitledPane userRegistrationPane;

    @FXML
    private TitledPane numberRangePane;

    @FXML
    private TitledPane topDownPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserRegistration.installAt(userRegistrationPane.contentProperty());
        NumberRange.installAt(numberRangePane.contentProperty());
        TopDownView.installAt(topDownPane.contentProperty());
    }
}
