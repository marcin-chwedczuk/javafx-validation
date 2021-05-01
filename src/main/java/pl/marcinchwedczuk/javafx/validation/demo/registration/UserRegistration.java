package pl.marcinchwedczuk.javafx.validation.demo.registration;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import pl.marcinchwedczuk.javafx.validation.demo.Demo;
import pl.marcinchwedczuk.javafx.validation.extra.UiBindings;
import pl.marcinchwedczuk.javafx.validation.extra.ValidationDecorator;
import pl.marcinchwedczuk.javafx.validation.extra.ValidationDecorator2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserRegistration implements Initializable {
    public static UserRegistration installAt(ObjectProperty<Node> contentProperty) {
        try {
            FXMLLoader loader = new FXMLLoader(UserRegistration.class.getResource("UserRegistration.fxml"));

            Node subView = loader.load();
            contentProperty.setValue(subView);

            UserRegistration controller = (UserRegistration) loader.getController();
            return controller;
        } catch (IOException e) {
            contentProperty.setValue(null);
            throw new RuntimeException(e);
        }
    }

    private final UserRegistrationViewModel viewModel = new UserRegistrationViewModel();

    @FXML
    private TextField usernameF;
    @FXML
    private ValidationDecorator2 usernameE;
    @FXML
    private Label modelUsernameF;

    @FXML
    private TextField passwordF;
    @FXML
    private Label modelPasswordF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UiBindings.biBind(usernameF, viewModel.username);
        usernameE.displayErrorsFor(viewModel.username);

        modelUsernameF.textProperty()
                .bind(viewModel.username.modelValueProperty());
    }

    @FXML
    private void registerUser() {
        viewModel.registerUser();
    }
}
