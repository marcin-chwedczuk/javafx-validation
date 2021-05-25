package pl.marcinchwedczuk.javafx.validation.validators;

import org.junit.jupiter.api.Test;
import pl.marcinchwedczuk.javafx.validation.Explanation;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectValidators_RequiredValidatorTest extends BaseValidatorTest {
    @Test
    void non_null_value_is_valid() {
        Validator<Object> validator = ObjectValidators.required().create();

        ValidationResult<String> result = validator.validate("foo");

        assertValidResultWithValue(result, "foo");
    }

    @Test
    void null_value_is_invalid() {
        Validator<Object> validator = ObjectValidators.required().create();

        ValidationResult<String> result = validator.<String>validate(null);

        assertInvalidResultWithValue(result, null);

        assertObjections(result.objections)
                .isEqualTo(List.of(
                        Objections.error("Value is required.")
                ));
    }

    @Test
    void supports_formatted_message() {
        Validator<Object> validator = ObjectValidators.required()
                .withExplanation(Explanation.of(
                    "this is message with %d and %s", 123, "foo"))
                .create();

        ValidationResult<Object> result = validator.validate(null);

        assertObjections(result.objections)
                .isEqualTo(List.of(
                        Objections.error("this is message with 123 and foo")
                ));
    }
}