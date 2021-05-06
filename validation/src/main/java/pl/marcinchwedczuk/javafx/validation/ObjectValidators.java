package pl.marcinchwedczuk.javafx.validation;

import java.util.Objects;

public class ObjectValidators {
    private ObjectValidators() { }

    public static Validator<Object> required() {
        return required("Value is required.");
    }

    public static Validator<Object> required(String message) {
        Objects.requireNonNull(message);

        return new Validator<>() {
            @Override
            public <VALIDATED> ValidationResult<VALIDATED> validate(VALIDATED value) {
                boolean isValid = (value != null);
                return new ValidationResult<>(value, Objections.errorIf(!isValid, message));
            }
        };
    }
}