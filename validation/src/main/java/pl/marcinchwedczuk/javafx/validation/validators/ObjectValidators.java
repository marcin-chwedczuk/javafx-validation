package pl.marcinchwedczuk.javafx.validation.validators;

import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.Objects;

public class ObjectValidators {
    private ObjectValidators() {
    }

    public static <T> Validator<T> required() {
        return required("Value is required.");
    }

    public static <T> Validator<T> required(String message) {
        return required("%s", message);
    }

    public static <T>
    Validator<T> required(String messageFormat, Object... args) {
        Objects.requireNonNull(messageFormat);

        return new Validator<>() {
            @Override
            public <TT extends T> ValidationResult<TT> validate(TT value) {
                boolean isValid = (value != null);
                if (isValid) {
                    return ValidationResult.success(value);
                }
                else {
                    String message = String.format(messageFormat, args);
                    return ValidationResult.failure(value, Objections.error(message));
                }
            }
        };
    }
}
