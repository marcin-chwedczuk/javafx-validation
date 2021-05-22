package pl.marcinchwedczuk.javafx.validation.validators;

import org.assertj.core.api.ListAssert;
import pl.marcinchwedczuk.javafx.validation.BaseUnitTest;
import pl.marcinchwedczuk.javafx.validation.Objection;
import pl.marcinchwedczuk.javafx.validation.ObjectionsList;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseValidatorTest extends BaseUnitTest {
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
