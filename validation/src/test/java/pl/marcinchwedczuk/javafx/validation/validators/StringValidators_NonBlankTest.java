package pl.marcinchwedczuk.javafx.validation.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.List;

class StringValidators_NonBlankTest extends BaseValidatorTest {
    @Test
    void null_is_considered_valid() {
        Validator<String> validator = StringValidators.nonBlank().create();

        ValidationResult<String> result = validator.validate(null);

        assertValidResultWithValue(result, null);
    }

    @Test
    void non_blank_string_is_considered_valid() {
        Validator<String> validator = StringValidators.nonBlank().create();

        ValidationResult<String> result = validator.validate("foo");

        assertValidResultWithValue(result, "foo");
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "   ", "\t", "\n", "\r\n" })
    void blank_string_is_considered_invalid(String blank) {
        Validator<String> validator = StringValidators.nonBlank().create();

        ValidationResult<String> result = validator.validate(blank);

        assertInvalidResultWithValue(result, blank);

        assertObjections(result.objections)
                .isEqualTo(List.of(
                        Objections.error("Value is required.")
                ));
    }

    @Test
    void formats_error_message() {
        Validator<String> validator = StringValidators.nonBlank()
                .withExplanation("Invalid value with %d and %s", 123, "foo")
                .create();

        ValidationResult<String> result = validator.validate("");

        assertObjections(result.objections)
                .isEqualTo(List.of(
                        Objections.error("Invalid value with 123 and foo")
                ));
    }
}