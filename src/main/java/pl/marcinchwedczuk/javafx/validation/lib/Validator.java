package pl.marcinchwedczuk.javafx.validation.lib;

@FunctionalInterface
public interface Validator<T> {
    ValidationResult<T> validate(T value);
}
