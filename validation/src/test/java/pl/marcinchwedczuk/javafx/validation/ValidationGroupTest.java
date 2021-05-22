package pl.marcinchwedczuk.javafx.validation;

import org.junit.jupiter.api.Test;
import pl.marcinchwedczuk.javafx.validation.converters.Converters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.marcinchwedczuk.javafx.validation.converters.Converters.stringIntegerConverter;

class ValidationGroupTest extends BaseUnitTest {
    Input<String, Integer> inputA = new Input<>(stringIntegerConverter());
    Input<String, Integer> inputB = new Input<>(stringIntegerConverter());

    ValidationGroup validationGroup = new ValidationGroup(inputA, inputB);

    {
        inputA.reset(0);
        inputB.reset(0);
    }

    @Test
    void returns_valid_when_all_inputs_are_valid() {
        boolean isValid = validationGroup.validate();
        assertThat(isValid)
                .isTrue();
    }

    @Test
    void returns_invalid_when_at_least_one_input_is_invalid() {
        inputA.setUiValue("abc");

        boolean isValid = validationGroup.validate();
        assertThat(isValid)
                .isFalse();
    }

    @Test
    void returns_validation_state_of_group_of_inputs() {
        // After reset(0) validation is not run
        assertThat(validationGroup.getValidationState())
                .isEqualTo(ValidationState.NOT_RUN);

        validationGroup.validate();
        assertThat(validationGroup.getValidationState())
                .isEqualTo(ValidationState.VALID);

        // Invalid value is set on one of the fields.
        // This will trigger validation and also it will
        // update validationGroup's validationState.
        inputA.setUiValue("abc");
        assertThat(validationGroup.getValidationState())
                .isEqualTo(ValidationState.INVALID);

        inputA.setUiValue("123");
        assertThat(validationGroup.getValidationState())
                .isEqualTo(ValidationState.VALID);
    }
}