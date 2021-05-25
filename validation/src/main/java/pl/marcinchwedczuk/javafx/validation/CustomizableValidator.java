package pl.marcinchwedczuk.javafx.validation;

public interface CustomizableValidator<T> {
    CustomizableValidator<T> withExplanation(Explanation explanation);

    default CustomizableValidator<T> withExplanation(String message) {
        return this.withExplanation(Explanation.of(message));
    }

    default CustomizableValidator<T> withExplanation(String messageFormat, Object... args) {
        return this.withExplanation(Explanation.of(messageFormat, args));
    }

    Validator<T> create();
}
