package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static pl.marcinchwedczuk.javafx.validation.lib.ObjectionSeverity.ERROR;

public class ValidationResult<V> {
    public final V value;
    public final List<Objection> objections;

    public ValidationResult(V value, List<Objection> objections) {
        this.value = value;
        this.objections = List.copyOf(objections);
    }

    public boolean isValid() {
        // TODO: Create objections List type
        return !Objections.containsError(objections);
    }

    public static <V> ValidationResult<V> merge(List<ValidationResult<V>> results) {
        // TODO: Args validation
        V value = results.get(0).value;

        List<Objection> errors = results.stream()
                .flatMap(r -> r.objections.stream())
                .collect(toList());

        return new ValidationResult<>(value, errors);
    }

    public static <V> ValidationResult<V> success(V value) {
        return new ValidationResult<>(value, List.of());
    }
}
