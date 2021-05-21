package pl.marcinchwedczuk.javafx.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ValidationResult<V> {
    public final V value;
    public final List<Objection> objections;

    private ValidationResult(V value, List<Objection> objections) {
        this.value = value;
        // This list will be sorted by priority. We need a mutable collection.
        this.objections = objections;
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

    public static <V> ValidationResult<V> failure(V value, Objection first, Objection... rest) {
        List<Objection> objections = new ArrayList<>();
        objections.add(first);
        objections.addAll(Arrays.asList(rest));
        return new ValidationResult<V>(value, objections);
    }
}
