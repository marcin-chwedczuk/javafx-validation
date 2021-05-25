package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.property.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.marcinchwedczuk.javafx.validation.converters.Converters;
import pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators;
import pl.marcinchwedczuk.javafx.validation.validators.ObjectValidators;
import pl.marcinchwedczuk.javafx.validation.validators.StringValidators;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.marcinchwedczuk.javafx.validation.converters.Converters.stringIntegerConverter;
import static pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators.RangeOptions.ALLOW_EMPTY_RANGE;
import static pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators.validRangeWithStart;

class InputTest extends BaseUnitTest {
    Input<String, Integer> intInput = new Input<>(stringIntegerConverter())
            .withUiValidators(
                    ObjectValidators.required()
                            .withExplanation("value_is_required"))
            .withModelValidator(
                    IntegerValidators.between(0, 1024)
            );

    @Test
    void setting_valid_ui_value_sets_model_value() {
        intInput.setUiValue("123");

        assertThat(intInput.getModelValue())
                .isEqualTo(123);
    }

    @Test
    void setting_model_value_sets_ui_value() {
        intInput.setModelValue(555);

        assertThat(intInput.getUiValue())
                .isEqualTo("555");
    }

    @Test
    void settings_invalid_ui_value_nulls_model_value_and_sets_invalid_flag() {
        intInput.setUiValue("abc");

        assertThat(intInput.getModelValue())
                .isNull();

        assertThat(intInput.getValidationState())
                .isEqualTo(ValidationState.INVALID);
    }

    @Test
    void setting_valid_ui_value_after_invalid_one_sets_model_value_and_clears_invalid_flag() {
        intInput.setUiValue("abc");
        intInput.setUiValue("123");

        assertThat(intInput.getModelValue())
                .isEqualTo(123);

        assertThat(intInput.getValidationState())
                .isEqualTo(ValidationState.VALID);
    }

    @Test
    void setting_invalid_model_value_sets_ui_value_and_sets_invalid_flag() {
        intInput.setModelValue(-1); // Outside range

        assertThat(intInput.getUiValue())
                .isEqualTo("-1");

        assertThat(intInput.getValidationState())
                .isEqualTo(ValidationState.INVALID);
    }

    @Test
    void reset_clear_validation_flag_and_sets_model_value() {
        intInput.setModelValue(-1);

        // Reset to invalid value
        intInput.reset(-1);

        assertThat(intInput.getUiValue())
                .isEqualTo("-1");

        assertThat(intInput.getModelValue())
                .isEqualTo(-1);

        assertThat(intInput.getValidationState())
                .isEqualTo(ValidationState.NOT_RUN);
    }

    @Test
    void pristine_flag_is_correctly_set() {
        // Is pristine when UI value was not changed by the user
        assertThat(intInput.isPristine())
                .isTrue();

        intInput.setUiValue("123");
        assertThat(intInput.isPristine())
                .isFalse();

        intInput.setModelValue(321);
        assertThat(intInput.isPristine())
                .isFalse();

        // Reset resets pristine value
        intInput.reset();
        assertThat(intInput.isPristine())
                .isTrue();

        // Settings invalid UI value also sets pristine
        intInput.reset();
        intInput.setUiValue("abc");
        assertThat(intInput.isPristine())
                .isFalse();

        // Setting invalid Model value _preserves_ pristine value
        intInput.reset();
        intInput.setModelValue(-100);
        assertThat(intInput.isPristine())
                .isTrue();
    }

    @Test
    void objections_returns_list_of_validation_problems() {
        assertThat(intInput.getObjections().asList())
                .isEmpty();

        // Value from UI Validator
        intInput.setUiValue(null);
        assertThat(intInput.getObjections().asList())
                .isEqualTo(List.of(Objections.error("value_is_required")));

        // Value from converter
        intInput.setUiValue("abc");
        assertThat(intInput.getObjections().asList())
                .isEqualTo(List.of(Objections.error("Cannot convert 'abc' to a number.")));

        // Value from model validator
        intInput.setUiValue("-1");
        assertThat(intInput.getObjections().asList())
                .isEqualTo(List.of(Objections.error("-1 must be between 0 and 1023.")));
    }

    @Nested
    class handling_validator_dependencies {
        ObjectProperty<Integer> dependencyProp = new SimpleObjectProperty<>(0);
        Input<String, Integer> dependencyInput = new Input<>(stringIntegerConverter())
                .withModelValidator(validRangeWithStart(dependencyProp, ALLOW_EMPTY_RANGE));

        @Test
        void rerun_validation_when_validator_dependency_changes_and_input_is_not_pristine() {
            dependencyProp.setValue(0);
            dependencyInput.setUiValue("100");
            assertThat(dependencyInput.getValidationState())
                    .isEqualTo(ValidationState.VALID);

            dependencyProp.setValue(101);
            assertThat(dependencyInput.getValidationState())
                    .isEqualTo(ValidationState.INVALID);

            dependencyProp.setValue(50);
            assertThat(dependencyInput.getValidationState())
                    .isEqualTo(ValidationState.VALID);
        }

        @Test
        void ignores_validator_dependencies_when_input_is_pristine() {
            // Input is pristine
            dependencyInput.reset(100);
            assertThat(dependencyInput.getValidationState())
                    .isEqualTo(ValidationState.NOT_RUN);

            dependencyProp.setValue(101);
            dependencyProp.setValue(50);
            assertThat(dependencyInput.getValidationState())
                    .isEqualTo(ValidationState.NOT_RUN);

            dependencyProp.setValue(50);

            // Force validation to run, remove pristine flag
            dependencyInput.reevaluateUiValue();
            assertThat(dependencyInput.getValidationState())
                    .isEqualTo(ValidationState.VALID);
        }
    }
}