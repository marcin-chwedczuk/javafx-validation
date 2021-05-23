package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.Observable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class ComposableValidator<T> implements Validator<T> {
    private final String name;
    private final Predicate<T> isValid;
    private final Collection<Observable> dependencies;
    private final Map<String, Object> explanationVariables;
    private final Explanation explanation;

    public ComposableValidator(String name,
                               Predicate<T> isValid,
                               Collection<Observable> dependencies,
                               Map<String, Object> explanationVariables,
                               Explanation explanation) {
        this.name = Objects.requireNonNull(name);
        this.isValid = Objects.requireNonNull(isValid);

        this.dependencies = List.copyOf(Objects.requireNonNull(dependencies));
        this.dependencies.forEach(Objects::requireNonNull);

        // TODO: value variable is reserved
        this.explanationVariables = Objects.requireNonNull(explanationVariables);
        this.explanation = Objects.requireNonNull(explanation);
    }

    public <TT extends T>
    ValidationResult<TT> validate(TT value) {
        if (isValid.test(value)) {
            return ValidationResult.success(value);
        } else {
            Objection objection = Objections.error(
                    explanation.explain(value, explanationVariables));

            return ValidationResult.failure(value, objection);
        }
    }

    @Override
    public Collection<Observable> dependencies() {
        return dependencies;
    }

    @Override
    public String toString() {
        return String.format("Validator[%s]", name);
    }
}
