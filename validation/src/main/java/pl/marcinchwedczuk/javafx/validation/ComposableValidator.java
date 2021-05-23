package pl.marcinchwedczuk.javafx.validation;

import java.util.Objects;
import java.util.function.Predicate;

public class ComposableValidator<T> implements Validator<T> {
    private final String name;
    private final Predicate<T> isValid;
    private final Explanation explanation;

    public ComposableValidator(String name, Predicate<T> isValid, Explanation explanation) {
        this.name = Objects.requireNonNull(name);
        this.isValid = Objects.requireNonNull(isValid);
        this.explanation = Objects.requireNonNull(explanation);
    }

    public <TT extends T>
    ValidationResult<TT> validate(TT value) {
        if (isValid.test(value)) {
            return ValidationResult.success(value);
        } else {
            Objection objection = Objections.error(explanation.explain());
            return ValidationResult.failure(value, objection);
        }
    }

    @Override
    public String toString() {
        return String.format("Validator[%s]", name);
    }
}
