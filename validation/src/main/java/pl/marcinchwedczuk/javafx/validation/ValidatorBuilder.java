package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.Observable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ValidatorBuilder {
    private ValidatorBuilder() { }

    public static <T>
    SetNameMandatoryStep<T> newValidator() {
        return new BuilderImpl<>();
    }

    public static class BuilderImpl<T> implements
            SetNameMandatoryStep<T>,
            SetPredicateMandatoryStep<T>,
            SetDefaultExplanationMandatoryStep<T>,
            SetDependenciesOptionalStep<T>,
            SetExplanationVariablesOptionalStep<T>,
            Builder<T>
    {
        private String name;
        private Predicate<T> isValid;
        private Collection<Observable> dependencies;
        private Map<String, Object> explanationVariables;
        private Explanation explanation;

        public BuilderImpl() {
            this(null,
                    null,
                    List.of(),
                    Map.of(),
                    null);
        }

        protected BuilderImpl(String name,
                              Predicate<T> isValid,
                              Collection<Observable> dependencies,
                              Map<String, Object> explanationVariables,
                              Explanation explanation)
        {
            this.name = name;
            this.isValid = isValid;
            this.dependencies = dependencies;
            this.explanationVariables = explanationVariables;
            this.explanation = explanation;
        }

        @Override
        public SetPredicateMandatoryStep<T> withName(String name) {
            return new BuilderImpl<>(
                    name,
                    this.isValid,
                    this.dependencies,
                    this.explanationVariables,
                    this.explanation);
        }

        @Override
        public SetPredicateMandatoryStep<T> withName(String nameFormat, Object... args) {
            String name = String.format(nameFormat, args);
            return new BuilderImpl<>(
                    name,
                    this.isValid,
                    this.dependencies,
                    this.explanationVariables,
                    this.explanation);
        }

        @Override
        public SetDependenciesOptionalStep<T> withPredicate(Predicate<T> isValid) {
            return new BuilderImpl<>(
                    this.name,
                    isValid,
                    this.dependencies,
                    this.explanationVariables,
                    this.explanation);
        }

        @Override
        public SetExplanationVariablesOptionalStep<T> withDependencies(Collection<Observable> dependencies) {
            return new BuilderImpl<>(
                    this.name,
                    this.isValid,
                    dependencies,
                    this.explanationVariables,
                    this.explanation);
        }

        @Override
        public SetDefaultExplanationMandatoryStep<T> withExplanationVariables(Map<String, Object> explanationVariables) {
            return new BuilderImpl<>(
                    this.name,
                    this.isValid,
                    this.dependencies,
                    explanationVariables,
                    this.explanation);
        }

        @Override
        public Builder<T> withExplanation(Explanation explanation) {
            return new BuilderImpl<>(
                    this.name,
                    this.isValid,
                    this.dependencies,
                    this.explanationVariables,
                    explanation);
        }

        @Override
        public Validator<T> build() {
            return new ComposableValidator<>(
                    this.name,
                    this.isValid,
                    this.dependencies,
                    this.explanationVariables,
                    this.explanation);
        }
    }

    public interface SetNameMandatoryStep<T> {
        SetPredicateMandatoryStep<T> withName(String name);
        SetPredicateMandatoryStep<T> withName(String name, Object... args);
    }

    public interface SetPredicateMandatoryStep<T> {
        SetDependenciesOptionalStep<T> withPredicate(Predicate<T> predicate);
    }

    public interface SetDependenciesOptionalStep<T> extends SetExplanationVariablesOptionalStep<T> {
        SetExplanationVariablesOptionalStep<T> withDependencies(Collection<Observable> dependencies);
    }

    public interface SetExplanationVariablesOptionalStep<T> extends SetDefaultExplanationMandatoryStep<T> {
        SetDefaultExplanationMandatoryStep<T> withExplanationVariables(Map<String, Object> variables);
    }

    public interface SetDefaultExplanationMandatoryStep<T> {
        Builder<T> withExplanation(Explanation explanation);
    }

    public interface Builder<T> {
        Builder<T> withExplanation(Explanation explanation);

        default Builder<T> withExplanation(String message) {
            return this.withExplanation(Explanation.of(message));
        }

        default Builder<T> withExplanation(String messageFormat, Object... args) {
            return this.withExplanation(Explanation.of(messageFormat, args));
        }

        Validator<T> build();
    }
}
