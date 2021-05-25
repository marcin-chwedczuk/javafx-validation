package pl.marcinchwedczuk.javafx.validation.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.List;

class StringValidators_MatchesRegexTest extends BaseValidatorTest {

    @Test
    void null_is_valid() {
        Validator<String> validator = StringValidators
                .matchesRegex("[0-9]+")
                .withExplanation("does_not_match")
                .create();

        ValidationResult<String> result = validator.validate(null);

        assertValidResultWithValue(result, null);
    }

    @ParameterizedTest
    @ValueSource(strings = { "0", "11", "937274" })
    void returns_valid_when_input_matches_regex(String input) {
        Validator<String> validator = StringValidators
                .matchesRegex("[0-9]+")
                .withExplanation("does_not_match")
                .create();

        ValidationResult<String> result = validator.validate(input);

        assertValidResultWithValue(result, input);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "11x", "abc", "\t\t", "abc123" })
    void returns_invalid_when_input_does_not_match_regex(String input) {
        Validator<String> validator = StringValidators
                .matchesRegex("[0-9]+")
                .withExplanation("does_not_match")
                .create();

        ValidationResult<String> result = validator.validate(input);

        assertInvalidResultWithValue(result, input);

        assertObjections(result.objections)
                .isEqualTo(List.of(Objections.error(
                        "does_not_match")));
    }
}