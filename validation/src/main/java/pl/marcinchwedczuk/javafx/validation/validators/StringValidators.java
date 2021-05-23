package pl.marcinchwedczuk.javafx.validation.validators;

import pl.marcinchwedczuk.javafx.validation.*;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class StringValidators {
    private StringValidators() {
    }

    public static
    ValidatorBuilder.Builder<String> nonBlank() {
        return ValidatorBuilder.<String>newValidator()
                .withName("nonBlank")
                .withPredicate(s -> (s == null) || !s.isBlank())
                .withExplanation(Explanation.of("Value is required."));
    }

    public static
    ValidatorBuilder.Builder<String> hasLength(int min, int maxExcluding) {
        return ValidatorBuilder.<String>newValidator()
                .withName("hasLength(%d, %d)", min, maxExcluding)
                .withPredicate(value -> {
                    int length = (value == null) ? 0 : value.length();
                    boolean isValid = (value == null) || (min <= length && length < maxExcluding);
                    return isValid;
                })
                .withExplanationVariables(Map.of(
                        "minLength", min,
                        "maxLength", maxExcluding-1
                ))
                .withExplanation(Explanation.of(
                    "Value must be at least #{minLength} and at most #{maxLength} characters long."));
    }

    public static Validator<String> matchesRegex(String regex, String message) {
        Objects.requireNonNull(regex);
        Objects.requireNonNull(message);

        Pattern pattern = Pattern.compile(regex);

        return new Validator<>() {
            @Override
            public <TT extends String> ValidationResult<TT> validate(TT value) {
                String safeValue = (value != null) ? value : "";
                boolean isValid = (value == null) || pattern.matcher(safeValue).matches();
                if (isValid) {
                    return ValidationResult.success(value);
                }
                else {
                    return ValidationResult.failure(value, Objections.error(message));
                }
            }
        };
    }
}
