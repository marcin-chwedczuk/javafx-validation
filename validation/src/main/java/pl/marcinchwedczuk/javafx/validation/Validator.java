package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.Observable;

import java.util.List;

public interface Validator<T> {
    <TT extends T> ValidationResult<TT> validate(TT value);

    default List<Observable> dependencies() {
        return List.of();
    }
}
