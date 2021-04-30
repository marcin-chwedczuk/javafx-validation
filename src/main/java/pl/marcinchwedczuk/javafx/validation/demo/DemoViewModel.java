package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.scene.control.Alert;
import pl.marcinchwedczuk.javafx.validation.lib.Converters;
import pl.marcinchwedczuk.javafx.validation.lib.Property;
import pl.marcinchwedczuk.javafx.validation.lib.StringValidators;

public class DemoViewModel {
    // Demo 1: username & password
    public final Property<String, String> username =
            new Property<String, String>(Converters.identityConverter())
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