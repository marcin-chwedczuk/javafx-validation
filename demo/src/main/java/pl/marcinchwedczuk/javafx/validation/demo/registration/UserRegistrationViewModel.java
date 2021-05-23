package pl.marcinchwedczuk.javafx.validation.demo.registration;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import pl.marcinchwedczuk.javafx.validation.converters.Converters;
import pl.marcinchwedczuk.javafx.validation.Input;
import pl.marcinchwedczuk.javafx.validation.validators.StringValidators;
import pl.marcinchwedczuk.javafx.validation.ValidationGroup;
import pl.marcinchwedczuk.javafx.validation.demo.mainwindow.UiService;

import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.ValidationState.INVALID;

public class UserRegistrationViewModel {
    private final UiService uiService;

    public final Input<String, String> username =
            new Input<String, String>(Converters.identityConverter())
                    .withUiValidators(
                            StringValidators.nonBlank(),
                            StringValidators.hasLength(2, 16),
                            StringValidators.matchesRegex("[_a-z][_a-z0-9]+")
                                .withExplanation(
                                    "Username can only consists of underscore, " +
                                            "lower-case letters and digits and cannot start with a digit."));

    public final Input<String, String> password =
            new Input<String, String>(Converters.identityConverter())
                    .withUiValidators(
                            StringValidators.nonBlank(),
                            StringValidators.hasLength(8, Integer.MAX_VALUE)
                                    .withExplanation("Password must have at least 8 characters."),
                            StringValidators.matchesRegex("(?=.*[A-Z]).*")
                                    .withExplanation("Password must contain an upper-case letter."),
                            StringValidators.matchesRegex("(?=.*[a-z]).*")
                                    .withExplanation("Password must contain a lower-case letter."),
                            StringValidators.matchesRegex("(?=.*[0-9]).*")
                                    .withExplanation("Password must contain a digit."),
                            StringValidators.matchesRegex("(?=.*[#?!@$%^&*-]).*")
                                    .withExplanation("Password must contain a special character."));

    private final ValidationGroup userRegistrationForm = new ValidationGroup(
            username,
            password);

    private final BooleanProperty showErrorBanner = new SimpleBooleanProperty(false);

    public UserRegistrationViewModel(UiService uiService) {
        this.uiService = Objects.requireNonNull(uiService);
    }

    public void registerUser() {
        if (!userRegistrationForm.validate()) {
            showErrorBanner.set(true);
            return;
        }

        uiService.infoDialog(String.format(
                "Registering user '%s' with password '%s'!",
                username.getModelValue(),
                password.getModelValue()));

        showErrorBanner.set(false);
        username.reset();
        password.reset();
    }

    public BooleanBinding registrationFormInvalid() {
        return userRegistrationForm.validationStateProperty().isEqualTo(INVALID);
    }

    public ReadOnlyBooleanProperty showErrorBannerProperty() {
        return showErrorBanner;
    }
}
