package pl.marcinchwedczuk.javafx.validation;

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
            Builder<T>
    {
        private String name;
        private Predicate<T> isValid;
        private Explanation explanation;

        public BuilderImpl() {
            this(null, null, null);
        }

        protected BuilderImpl(String name,
                              Predicate<T> isValid,
                              Explanation explanation)
        {
            this.name = name;
            this.isValid = isValid;
            this.explanation = explanation;
        }

        @Override
        public SetPredicateMandatoryStep<T> withName(String name) {
            return new BuilderImpl<>(name, this.isValid, this.explanation);
        }

        @Override
        public SetPredicateMandatoryStep<T> withName(String nameFormat, Object... args) {
            String name = String.format(nameFormat, args);
            return new BuilderImpl<>(name, this.isValid, this.explanation);
        }

        @Override
        public SetDefaultExplanationMandatoryStep<T> withPredicate(Predicate<T> isValid) {
            return new BuilderImpl<>(this.name, isValid, this.explanation);
        }

        @Override
        public Builder<T> withExplanation(Explanation explanation) {
            return new BuilderImpl<>(this.name, this.isValid, explanation);
        }

        @Override
        public Validator<T> build() {
            return new ComposableValidator<>(this.name, this.isValid, this.explanation);
        }
    }

    public interface SetNameMandatoryStep<T> {
        SetPredicateMandatoryStep<T> withName(String name);
        SetPredicateMandatoryStep<T> withName(String name, Object... args);
    }

    public interface SetPredicateMandatoryStep<T> {
        SetDefaultExplanationMandatoryStep<T> withPredicate(Predicate<T> predicate);
    }

    public interface SetDefaultExplanationMandatoryStep<T> {
        Builder<T> withExplanation(Explanation explanation);
    }

    public interface Builder<T> {
        Builder<T> withExplanation(Explanation explanation);

        Validator<T> build();
    }
}
