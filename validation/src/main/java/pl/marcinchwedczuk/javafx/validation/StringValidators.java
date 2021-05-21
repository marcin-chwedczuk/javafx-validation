package pl.marcinchwedczuk.javafx.validation;

import java.util.Objects;
import java.util.regex.Pattern;

public class StringValidators {
    private StringValidators() {
    }

    public static Validator<String> nonBlank() {
        return nonBlank("Value is required.");
    }

    public static Validator<String> nonBlank(String message) {
        Objects.requireNonNull(message);
        return new Validator<>() {
            @Override
            public <TT extends String> ValidationResult<TT> validate(TT value) {
                boolean isValid = (value != null) && !value.isBlank();
                return new ValidationResult<>(value, Objections.errorIf(!isValid, message));
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
                boolean isValid = (min <= length && length < maxExcluding);
                return new ValidationResult<>(value,
                        Objections.errorIf(!isValid, message));
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
                boolean isValid = pattern.matcher(safeValue).matches();
                return new ValidationResult<>(value,
                        Objections.errorIf(!isValid, message));
            }
        };
    }
}
