package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    private TextField usernameF;

    @FXML
    private TextField passwordF;

    @FXML
    private Label modelUsernameF;

    @FXML
    private Label modelPasswordF;

    private final DemoViewModel viewModel = new DemoViewModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameF.textProperty()
                .bindBidirectional(viewModel.username.uiValueProperty());

        modelUsernameF.textProperty()
                .bind(viewModel.username.modelValueProperty());
    }


    @FXML
    private void registerUser() {
        viewModel.registerUser();
    }
}
