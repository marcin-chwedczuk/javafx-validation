package pl.marcinchwedczuk.javafx.validation.validators;

import pl.marcinchwedczuk.javafx.validation.ValidationResult;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseValidatorTest {
    protected <V> void assertValidResultWithValue(ValidationResult<V> result, V expectedValue) {
        assertThat(result.isValid())
                .isTrue();

        assertThat(result.value)
                .isEqualTo(expectedValue);

        assertThat(result.objections)
                .isEmpty();
    }

    protected <V> void assertInvalidResultWithValue(ValidationResult<V> result, V expectedValue) {
        assertThat(result.isValid())
                .isFalse();

        assertThat(result.value)
                .isEqualTo(expectedValue);

        assertThat(result.objections)
                .isNotEmpty();
    }
}
