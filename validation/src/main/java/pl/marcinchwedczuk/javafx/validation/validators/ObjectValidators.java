package pl.marcinchwedczuk.javafx.validation.validators;

import pl.marcinchwedczuk.javafx.validation.*;

import java.util.Objects;

public class ObjectValidators {
    private ObjectValidators() {
    }

    public static <T>
    ValidatorBuilder.Builder<T> required() {
        return ValidatorBuilder.<T>newValidator()
                .withName("required")
                .withPredicate(Objects::nonNull)
                .withExplanation(Explanation.of("Value is required."));
    }
}
