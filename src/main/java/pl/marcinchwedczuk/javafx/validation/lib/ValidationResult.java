package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ValidationResult<V> {
    public final V value;
    public final List<ValidationError> errors;

    public ValidationResult(V value, List<ValidationError> errors) {
        this.value = value;
        this.errors = List.copyOf(errors);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public static <V> ValidationResult<V> merge(List<ValidationResult<V>> results) {
        // TODO: Args validation
        V value = results.get(0).value;

        List<ValidationError> errors = results.stream()
                .flatMap(r -> r.errors.stream())
                .collect(toList());

        return new ValidationResult<>(value, errors);
    }

    public static <V> ValidationResult<V> success(V value) {
        return new ValidationResult<>(value, List.of());
    }
}
