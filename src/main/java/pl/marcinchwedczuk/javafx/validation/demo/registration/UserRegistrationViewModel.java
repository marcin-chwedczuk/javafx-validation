package pl.marcinchwedczuk.javafx.validation.demo.registration;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.control.Alert;
import pl.marcinchwedczuk.javafx.validation.demo.UiService;
import pl.marcinchwedczuk.javafx.validation.lib.Converters;
import pl.marcinchwedczuk.javafx.validation.lib.Input;
import pl.marcinchwedczuk.javafx.validation.lib.StringValidators;
import pl.marcinchwedczuk.javafx.validation.lib.ValidationGroup;

import java.util.Objects;

public class UserRegistrationViewModel {
    private final UiService uiService;

    public final Input<String, String> username =
            new Input<String, String>(Converters.identityConverter())
                    .withUiValidators(
                            StringValidators.required(),
                            StringValidators.hasLength(2, 16),
                            StringValidators.matchesRegex("[_a-z][_a-z0-9]+",
                                    "Username can only consists of underscore, " +
                                    "lower-case letters and digits and cannot start with a digit."));


    public final Input<String, String> password =
            new Input<String, String>(Converters.identityConverter())
                .withUiValidators(
                        StringValidators.required(),
                        StringValidators.hasLength(8, Integer.MAX_VALUE, "Password must have at least 8 characters."),
                        StringValidators.matchesRegex("(?=.*[A-Z]).*", "Password must contain an upper-case letter."),
                        StringValidators.matchesRegex("(?=.*[a-z]).*", "Password must contain a lower-case letter."),
                        StringValidators.matchesRegex("(?=.*[0-9]).*", "Password must contain a digit."),
                        StringValidators.matchesRegex("(?=.*[#?!@$%^&*-]).*", "Password must contain a special character."));


    private final ValidationGroup userRegistrationForm = new ValidationGroup(
            username,
            password);

    public UserRegistrationViewModel(UiService uiService) {
        this.uiService = Objects.requireNonNull(uiService);
    }

    public void registerUser() {
        uiService.infoDialog(String.format(
                "Registering user '%s' with password '%s'!",
                username.getModelValue(),
                password.getModelValue()));
    }

    public ReadOnlyBooleanProperty registrationButtonEnabledProperty() {
        return userRegistrationForm.validProperty();
    }
}
