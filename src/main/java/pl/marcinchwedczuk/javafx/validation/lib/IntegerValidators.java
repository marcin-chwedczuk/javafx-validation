package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.Objects;

public class IntegerValidators {
    private IntegerValidators() {}

    /*
    public static Validator<Integer> relative(String message) {
        Objects.requireNonNull(message);

        return (value) -> {
            boolean isValid = (value != null) && !value.isBlank();
            return new ValidationResult<>(value, Objections.errorIf(!isValid, message));
        };
    }*/
}
