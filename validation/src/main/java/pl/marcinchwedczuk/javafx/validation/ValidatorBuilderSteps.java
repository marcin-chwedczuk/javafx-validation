package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.Observable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public class ValidatorBuilderSteps {
    private ValidatorBuilderSteps() { }

    public interface SetNameMandatoryStep<T> {
        SetPredicateMandatoryStep<T> withName(String name);

        default SetPredicateMandatoryStep<T> withName(String nameFormat, Object... args) {
            return withName(String.format(nameFormat, args));
        }
    }

    public interface SetPredicateMandatoryStep<T> {
        SetDependenciesOptionalStep<T> withPredicate(Predicate<T> predicate);
    }

    public interface SetDependenciesOptionalStep<T> extends SetDefaultExplanationMandatoryStep<T> {
        SetDefaultExplanationMandatoryStep<T> withDependencies(Collection<Observable> dependencies);

        default SetDefaultExplanationMandatoryStep<T> withDependencies(Observable... dependencies) {
            return withDependencies(Arrays.asList(dependencies));
        }
    }

    public interface SetDefaultExplanationMandatoryStep<T> {
        SetExplanationVariablesOptionalStep<T> withDefaultExplanation(Explanation explanation);
    }

    public interface SetExplanationVariablesOptionalStep<T> extends CustomizableValidator<T> {
        CustomizableValidator<T> withExplanationVariables(Map<String, Object> variables);
    }
}
