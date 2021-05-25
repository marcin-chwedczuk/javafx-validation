package pl.marcinchwedczuk.javafx.validation.validators;

import pl.marcinchwedczuk.javafx.validation.*;

import java.util.Objects;

public class ObjectValidators {
    private ObjectValidators() {
    }

    public static <T>
    CustomizableValidator<T> required() {
        return new ValidatorBuilder<T>()
                .withName("required")
                .withPredicate(Objects::nonNull)
                .withDefaultExplanation(Explanation.of("Value is required."));
    }
}
