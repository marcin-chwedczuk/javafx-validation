package pl.marcinchwedczuk.javafx.validation.validators;

import pl.marcinchwedczuk.javafx.validation.Objection;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.Objects;
import java.util.regex.Pattern;

public class StringValidators {
    private StringValidators() {
    }

    public static Validator<String> nonBlank() {
        return nonBlank("Value is required.");
    }
    public static Validator<String> nonBlank(String message) {
        return nonBlank("%s", message);
    }
    public static Validator<String> nonBlank(String messageFormat, Object... args) {
        Objects.requireNonNull(messageFormat);

        return new Validator<>() {
            @Override
            public <TT extends String> ValidationResult<TT> validate(TT value) {
                boolean isValid = (value != null) && !value.isBlank();
                if (isValid) {
                    return ValidationResult.success(value);
                }
                else {
                    String message = String.format(messageFormat, args);
                    return ValidationResult.failure(value, Objections.error(message));
                }
            }
        };
    }

    public static Validator<String> hasLength(int min, int maxExcluding) {
        String message = String.format(
                "Value must be at least %d and at most %d characters long.", min, maxExcluding - 1);
        return hasLength(min, maxExcluding, message);
    }

    public static Validator<String> hasLength(int min, int maxExcluding, String message) {
        Objects.requireNonNull(message);
        if (min < 0 || min >= maxExcluding) throw new IllegalArgumentException("min");

        return new Validator<>() {
            @Override
            public <TT extends String> ValidationResult<TT> validate(TT value) {
                int length = (value == null) ? 0 : value.length();
                boolean isValid = (value == null) || (min <= length && length < maxExcluding);
                if (isValid) {
                    return ValidationResult.success(value);
                }
                else {
                    return ValidationResult.failure(value, Objections.error(message));
                }
            }
        };
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
