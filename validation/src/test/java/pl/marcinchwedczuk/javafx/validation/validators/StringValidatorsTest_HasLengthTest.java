package pl.marcinchwedczuk.javafx.validation.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StringValidatorsTest_HasLengthTest extends BaseValidatorTest {
    @Test
    void null_is_considered_valid() {
        Validator<String> validator = StringValidators.hasLength(3, 7).build();

        ValidationResult<String> result = validator.validate(null);

        assertValidResultWithValue(result, null);
    }

    @ParameterizedTest
    @ValueSource(strings = { "abc", "abcd", "abcde", "abcdef" })
    void strings_within_length_range_are_considered_valid(String input) {
        Validator<String> validator = StringValidators.hasLength(3, 7).build();

        ValidationResult<String> result = validator.validate(input);

        assertValidResultWithValue(result, input);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "a", "ab", "abcdefX", "abcdefXY" })
    void strings_outside_length_range_are_considered_invalid(String input) {
        Validator<String> validator = StringValidators.hasLength(3, 7).build();

        ValidationResult<String> result = validator.validate(input);

        assertInvalidResultWithValue(result, input);

        assertObjections(result.objections)
                .isEqualTo(List.of(
                        Objections.error("Value must be at least 3 and at most 6 characters long.")
                ));
    }

    @Test
    void formats_error_message() {
        Validator<String> validator = StringValidators.hasLength(3, 7)
                .withExplanation("Invalid value with %d and %s", 123, "foo")
                .build();

        ValidationResult<String> result = validator.validate("");

        assertObjections(result.objections)
                .isEqualTo(List.of(
                        Objections.error("Invalid value with 123 and foo")
                ));
    }
}