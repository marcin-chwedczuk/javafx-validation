package pl.marcinchwedczuk.javafx.validation.validators;

import javafx.beans.property.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.marcinchwedczuk.javafx.validation.Objection;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators.RangeOptions.ALLOW_EMPTY_RANGE;
import static pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators.RangeOptions.DISALLOW_EMPTY_RANGE;

class IntegerValidators_ValidRangeWithStartTest extends BaseValidatorTest {
    private ObjectProperty<Integer> rangeStart = new SimpleObjectProperty<>(0);

    @Test
    void null_value_is_valid() {
        Validator<Integer> validator =
                IntegerValidators.validRangeWithStart(rangeStart, ALLOW_EMPTY_RANGE);

        ValidationResult<Integer> result = validator.validate(null);

        assertValidResultWithValue(result, null);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 10, 100, 999 })
    void proper_ranges_are_valid(int rangeEnd) {
        Validator<Integer> validator =
                IntegerValidators.validRangeWithStart(rangeStart, ALLOW_EMPTY_RANGE);

        ValidationResult<Integer> result = validator.validate(rangeEnd);

        assertValidResultWithValue(result, rangeEnd);
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, -5, -6, -100, -999 })
    void improper_ranges_are_invalid(int rangeEnd) {
        Validator<Integer> validator =
                IntegerValidators.validRangeWithStart(rangeStart, ALLOW_EMPTY_RANGE);

        ValidationResult<Integer> result = validator.validate(rangeEnd);

        assertInvalidResultWithValue(result, rangeEnd);


        String errorMessage = "Invalid range of numbers: #{number} (this number) must be greater or equal than 0."
                .replace("#{number}", Integer.toString(rangeEnd));

        assertObjections(result.objections)
                .isEqualTo(List.of(Objections.error(errorMessage)));
    }

    @Test
    void range_start_influences_validity() {
        Validator<Integer> validator =
                IntegerValidators.validRangeWithStart(rangeStart, ALLOW_EMPTY_RANGE);

        rangeStart.setValue(0);
        assertValidResultWithValue(validator.validate(3), 3);

        rangeStart.setValue(10);
        assertInvalidResultWithValue(validator.validate(3), 3);

        rangeStart.setValue(2);
        assertValidResultWithValue(validator.validate(3), 3);
    }

    @Test
    void allow_empty_range_option_works() {
        Validator<Integer> validator =
                IntegerValidators.validRangeWithStart(rangeStart, ALLOW_EMPTY_RANGE);

        rangeStart.setValue(123);
        assertValidResultWithValue(validator.validate(123), 123);
    }

    @Test
    void disallow_empty_range_option_works() {
        Validator<Integer> validator =
                IntegerValidators.validRangeWithStart(rangeStart, DISALLOW_EMPTY_RANGE);

        rangeStart.setValue(123);
        assertInvalidResultWithValue(validator.validate(123), 123);
    }
}