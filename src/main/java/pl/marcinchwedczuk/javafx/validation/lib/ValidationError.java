package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ValidationError {
    public final String message;

    private ValidationError(String message) {
        this.message = message;
    }

    public static ValidationError of(String message) {
        return new ValidationError(message);
    }

    public static List<ValidationError> onlyIf(boolean condition, String message) {
        return condition
                ? List.of(ValidationError.of(message))
                : List.of();
    }

    public static List<ValidationError> combine(List<ValidationError>... errors) {
        return Arrays.stream(errors)
                .flatMap(Collection::stream)
                .collect(toList());
    }
}
