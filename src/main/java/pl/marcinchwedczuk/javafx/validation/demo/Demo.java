package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.marcinchwedczuk.javafx.validation.extra.UiBindings;
import pl.marcinchwedczuk.javafx.validation.extra.ValidationDecorator;

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
            window.setMinWidth(640);
            window.setMinHeight(480);
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
    private TextField usernameF;
    @FXML
    private Label modelUsernameF;
    @FXML
    private ValidationDecorator usernameE;

    @FXML
    private TextField passwordF;
    @FXML
    private Label modelPasswordF;

    private final DemoViewModel viewModel = new DemoViewModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //usernameF.textProperty()
        //        .bindBidirectional(viewModel.username.uiValueProperty());

        UiBindings.biBind(usernameF.textProperty(), viewModel.username);
        // TODO: Create custom control that will add and remove Text nodes as needed
        // VBox with Text nodes + word wrap + elipsis...
        // viewModel.username.showErrorsOn(usernameE);
        /*
        usernameE.textProperty().bind(
                Bindings.createStringBinding(
                        () -> viewModel.username.validationErrorsProperty().getValue().stream()
                                .map(ve -> "* " + ve.message)
                                .collect(joining("\n")),
                        viewModel.username.validationErrorsProperty()));
         */
        usernameE.displayErrorsFor(viewModel.username);

        modelUsernameF.textProperty()
                .bind(viewModel.username.modelValueProperty());
    }

    @FXML
    private void registerUser() {
        viewModel.registerUser();
    }
}
