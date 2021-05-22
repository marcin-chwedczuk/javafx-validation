package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.Observable;

import java.util.Collection;
import java.util.List;

public interface Validator<T> {
    <TT extends T> ValidationResult<TT> validate(TT value);

    default Collection<Observable> dependencies() {
        return List.of();
    }
}
