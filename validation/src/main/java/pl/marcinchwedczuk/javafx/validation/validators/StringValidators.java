package pl.marcinchwedczuk.javafx.validation.validators;

import pl.marcinchwedczuk.javafx.validation.*;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class StringValidators {
    private StringValidators() {
    }

    public static CustomizableValidator<String> nonBlank() {
        return new ValidatorBuilder<String>()
                .withName("nonBlank")
                .withPredicate(s -> (s == null) || !s.isBlank())
                .withDefaultExplanation(Explanation.of("Value is required."));
    }

    public static CustomizableValidator<String> hasLength(int min, int maxExcluding) {
        // TODO: Add parameter validation

        return new ValidatorBuilder<String>()
                .withName("hasLength(%d, %d)", min, maxExcluding)
                .withPredicate(value -> {
                    int length = (value == null) ? 0 : value.length();
                    boolean isValid = (value == null) || (min <= length && length < maxExcluding);
                    return isValid;
                })
                .withDefaultExplanation(Explanation.of(
                    "Value must be at least #{minLength} and at most #{maxLength} characters long."))
                .withExplanationVariables(Map.of(
                        "minLength", min,
                        "maxLength", maxExcluding-1
                ));
    }

    public static CustomizableValidator<String> matchesRegex(String regex) {
        Objects.requireNonNull(regex);

        Pattern pattern = Pattern.compile(regex);

        return new ValidatorBuilder<String>()
                .withName("matchesRegex(%s)", pattern)
                .withPredicate(value -> {
                    String safeValue = (value != null) ? value : "";
                    boolean isValid = (value == null) || pattern.matcher(safeValue).matches();
                    return isValid;
                })
                .withDefaultExplanation(Explanation.of("Value must match regex '#{pattern}'."))
                .withExplanationVariables(Map.of(
                        "pattern", pattern.toString()
                ));
    }
}
