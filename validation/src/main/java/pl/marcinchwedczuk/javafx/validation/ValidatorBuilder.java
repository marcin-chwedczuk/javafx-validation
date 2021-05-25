package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.Observable;
import pl.marcinchwedczuk.javafx.validation.ValidatorBuilderSteps.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ValidatorBuilder<T> implements SetNameMandatoryStep<T> {
    @Override
    public SetPredicateMandatoryStep<T> withName(String name) {
        return new BuilderImpl<T>().withName(name);
    }

    private static class BuilderImpl<T> implements
            SetNameMandatoryStep<T>,
            SetPredicateMandatoryStep<T>,
            SetDependenciesOptionalStep<T>,
            SetDefaultExplanationMandatoryStep<T>,
            SetExplanationVariablesOptionalStep<T>,
            CustomizableValidator<T>
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
        public SetDependenciesOptionalStep<T> withPredicate(Predicate<T> isValid) {
            return new BuilderImpl<>(
                    this.name,
                    isValid,
                    this.dependencies,
                    this.explanationVariables,
                    this.explanation);
        }

        @Override
        public SetDefaultExplanationMandatoryStep<T> withDependencies(Collection<Observable> dependencies) {
            return new BuilderImpl<>(
                    this.name,
                    this.isValid,
                    dependencies,
                    this.explanationVariables,
                    this.explanation);
        }

        @Override
        public SetExplanationVariablesOptionalStep<T> withDefaultExplanation(Explanation explanation) {
            return new BuilderImpl<>(
                    this.name,
                    this.isValid,
                    this.dependencies,
                    this.explanationVariables,
                    explanation);
        }

        @Override
        public CustomizableValidator<T> withExplanationVariables(Map<String, Object> explanationVariables) {
            return new BuilderImpl<>(
                    this.name,
                    this.isValid,
                    this.dependencies,
                    explanationVariables,
                    this.explanation);
        }

        @Override
        public CustomizableValidator<T> withExplanation(Explanation explanation) {
            return new BuilderImpl<>(
                    this.name,
                    this.isValid,
                    this.dependencies,
                    this.explanationVariables,
                    explanation);
        }

        @Override
        public Validator<T> create() {
            return new ComposableValidator<>(
                    this.name,
                    this.isValid,
                    this.dependencies,
                    this.explanationVariables,
                    this.explanation);
        }
    }

}
