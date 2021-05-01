package pl.marcinchwedczuk.javafx.validation.demo.registration;

import javafx.scene.control.Alert;
import pl.marcinchwedczuk.javafx.validation.lib.Converters;
import pl.marcinchwedczuk.javafx.validation.lib.Input;
import pl.marcinchwedczuk.javafx.validation.lib.StringValidators;

public class UserRegistrationViewModel {
    // Demo 1: username & password
    public final Input<String, String> username =
            new Input<String, String>(Converters.identityConverter())
                    .withUiValidators(
                            StringValidators.required(),
                            StringValidators.hasLength(6, 10)
                    );
    // TODO: Regex

    public void registerUser() {
        info("User " + username.getModelValue() + " registered!");
    }

    private void info(String text) {
        // TODO: Move to UiService or something
        new Alert(Alert.AlertType.INFORMATION, text).showAndWait();
    }
}
