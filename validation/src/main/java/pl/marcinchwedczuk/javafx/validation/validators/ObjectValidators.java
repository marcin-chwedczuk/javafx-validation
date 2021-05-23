package pl.marcinchwedczuk.javafx.validation.validators;

import pl.marcinchwedczuk.javafx.validation.*;

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

    public static <T>
    ValidatorBuilder.Builder<T> required2() {
        return ValidatorBuilder.<T>newValidator()
                .withName("required")
                .withPredicate(Objects::nonNull)
                .withExplanation(Explanation.of("Value is required."));
    }
}
