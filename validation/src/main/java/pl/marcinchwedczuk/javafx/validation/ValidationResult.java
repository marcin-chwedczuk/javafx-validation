package pl.marcinchwedczuk.javafx.validation;

import java.util.*;

import static java.util.stream.Collectors.toUnmodifiableList;

public class ValidationResult<V> {
    public static <V>
    ValidationResult<V> success(V value) {
        return new ValidationResult<>(value, ObjectionsList.EMPTY);
    }

    public static <V>
    ValidationResult<V> failure(V value, Objection first, Objection... rest) {
        return new ValidationResult<V>(value, ObjectionsList.of(first, rest));
    }

    public final V value;
    public final ObjectionsList objections;

    private ValidationResult(V value, ObjectionsList objections) {
        this.value = value;
        this.objections = Objects.requireNonNull(objections);
    }

    public boolean isValid() {
        return !objections.containsError();
    }

    public static <V> ValidationResult<V> merge(Collection<ValidationResult<V>> results) {
        // TODO: Args validation
        V value = results.iterator().next().value;

        List<Objection> objections = results.stream()
                .flatMap(r -> r.objections.stream())
                .collect(toUnmodifiableList());

        return new ValidationResult<>(value, new ObjectionsList(objections));
    }
}
