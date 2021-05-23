package pl.marcinchwedczuk.javafx.validation;

import java.util.function.Predicate;

public class ValidatorBuilder {
    private ValidatorBuilder() { }

    public static <T>
    Itself<T> newValidator() {
        return new Itself<>();
    }

    public static class Itself<T> implements
            SetNameMandatoryStep<T>,
            SetPredicateMandatoryStep<T>,
            SetDefaultExplanationMandatoryStep<T>,
            CustomizationStepsAndBuild<T>
    {
        private String name;
        private Predicate<T> isValid;
        private Explanation explanation;

        public Itself() {
            this(null, null, null);
        }

        protected Itself(String name,
                         Predicate<T> isValid,
                         Explanation explanation)
        {
            this.name = name;
            this.isValid = isValid;
            this.explanation = explanation;
        }

        @Override
        public SetPredicateMandatoryStep<T> withName(String name) {
            return new Itself<>(name, this.isValid, this.explanation);
        }

        @Override
        public SetPredicateMandatoryStep<T> withName(String nameFormat, Object... args) {
            String name = String.format(nameFormat, args);
            return new Itself<>(name, this.isValid, this.explanation);
        }

        @Override
        public SetDefaultExplanationMandatoryStep<T> withPredicate(Predicate<T> isValid) {
            return new Itself<>(this.name, isValid, this.explanation);
        }

        @Override
        public CustomizationStepsAndBuild<T> withExplanation(Explanation explanation) {
            return new Itself<>(this.name, this.isValid, explanation);
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
        CustomizationStepsAndBuild<T> withExplanation(Explanation explanation);
    }

    public interface CustomizationStepsAndBuild<T> {
        CustomizationStepsAndBuild<T> withExplanation(Explanation explanation);

        Validator<T> build();
    }
}
