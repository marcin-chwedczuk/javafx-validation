package pl.marcinchwedczuk.javafx.validation.lib;

import javafx.beans.Observable;

import java.util.List;

public interface Validator<T> {
    <VALIDATED extends T> ValidationResult<VALIDATED> validate(VALIDATED value);

    default List<Observable> dependencies() {
        return List.of();
    }
}
