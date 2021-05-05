package pl.marcinchwedczuk.javafx.validation.demo.mainwindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import pl.marcinchwedczuk.javafx.validation.demo.range.NumberRange;
import pl.marcinchwedczuk.javafx.validation.demo.registration.UserRegistration;
import pl.marcinchwedczuk.javafx.validation.demo.topdown.TopDownView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.joining;

public class Demo implements Initializable {
    public static Demo show() {
        try {
            FXMLLoader loader = new FXMLLoader(Demo.class.getResource("Demo.fxml"));

            Stage window = new Stage();
            window.setTitle("Validation Demo");
            window.setScene(new Scene(loader.load()));
            window.setResizable(true);

            Demo controller = (Demo) loader.getController();

            window.sizeToScene();
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
