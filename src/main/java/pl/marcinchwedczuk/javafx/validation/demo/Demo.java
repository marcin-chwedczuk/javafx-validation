package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class Demo implements Initializable {
    public static Demo show() {
        try {
            FXMLLoader loader = new FXMLLoader(Demo.class.getResource("Demo.fxml"));

            Stage window = new Stage();
            window.setTitle("Validation Demo");
            window.setScene(new Scene(loader.load()));
            window.setMinWidth(640);
            window.setMinHeight(480);
            window.setResizable(true);

            Demo controller = (Demo)loader.getController();

            window.sizeToScene();
            window.show();

            return controller;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private TextField usernameF;
    @FXML private Label modelUsernameF;
    @FXML private Text usernameE;

    @FXML private TextField passwordF;
    @FXML private Label modelPasswordF;

    private final DemoViewModel viewModel = new DemoViewModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameF.textProperty()
                .bindBidirectional(viewModel.username.uiValueProperty());

        usernameE.textProperty().bind(
                Bindings.createStringBinding(
                        () -> viewModel.username.validationErrorsProperty().getValue().stream()
                                .map(ve -> "* " + ve.message)
                                .collect(joining("\n")),
                        viewModel.username.validationErrorsProperty()));

        modelUsernameF.textProperty()
                .bind(viewModel.username.modelValueProperty());
    }


    @FXML
    private void registerUser() {
        viewModel.registerUser();
    }
}
