package pl.marcinchwedczuk.javafx.validation.lib;

import javafx.beans.Observable;

import java.util.List;

@FunctionalInterface
public interface Validator<T> {
    ValidationResult<T> validate(T value);

    default List<Observable> dependencies() {
        return List.of();
    }
}
