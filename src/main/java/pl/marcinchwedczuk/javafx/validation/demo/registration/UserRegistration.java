package pl.marcinchwedczuk.javafx.validation.demo.registration;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.marcinchwedczuk.javafx.validation.demo.UiService;
import pl.marcinchwedczuk.javafx.validation.controls.Banner;
import pl.marcinchwedczuk.javafx.validation.extra.UiBindings;
import pl.marcinchwedczuk.javafx.validation.extra.ValidationDecorator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserRegistration implements Initializable {
    public static UserRegistration installAt(ObjectProperty<Node> contentProperty) {
        try {
            FXMLLoader loader = new FXMLLoader(UserRegistration.class.getResource("UserRegistration.fxml"));

            Node subView = loader.load();
            contentProperty.setValue(subView);

            return (UserRegistration) loader.getController();
        } catch (IOException e) {
            contentProperty.setValue(null);
            throw new RuntimeException(e);
        }
    }

    private final UserRegistrationViewModel viewModel = new UserRegistrationViewModel(new UiService()); // Bieda DI

    @FXML
    private TextField usernameF;
    @FXML
    private ValidationDecorator usernameE;
    @FXML
    private Label modelUsernameF;

    @FXML
    private TextField passwordF;
    @FXML
    private ValidationDecorator passwordE;
    @FXML
    private Label modelPasswordF;

    @FXML
    private Banner invalidBanner;

    @FXML
    private Button registerUserButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UiBindings.biBind(usernameF, viewModel.username);
        usernameE.displayErrorsFor(viewModel.username);

        UiBindings.biBind(passwordF, viewModel.password);
        passwordE.displayErrorsFor(viewModel.password);

        modelUsernameF.textProperty().bind(
                UiBindings.map2(viewModel.username.modelValueProperty(), viewModel.username.pristineProperty(), (modelValue, pristine) ->
                        String.format("%s (pristine: %s)", modelValue, pristine)));

        modelPasswordF.textProperty().bind(
                UiBindings.map2(viewModel.password.modelValueProperty(), viewModel.password.pristineProperty(), (modelValue, pristine) ->
                        String.format("%s (pristine: %s)", modelValue, pristine)));

        invalidBanner.visibleProperty()
                .bind(viewModel.showErrorBannerProperty());
        invalidBanner.managedProperty()
                .bind(viewModel.showErrorBannerProperty());
    }

    @FXML
    private void registerUser() {
        viewModel.registerUser();
    }

    public void validate(ActionEvent actionEvent) {

    }
}
