package pl.marcinchwedczuk.javafx.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConversionResultTest {

    @Test
    public void can_create_success_result() {
        ConversionResult<String, Integer> result = ConversionResult.success("123", 123);

        assertThat(result.uiValue)
                .isEqualTo("123");

        assertThat(result.modelValue)
                .isEqualTo(123);

        assertThat(result.isSuccessful())
                .isTrue();
    }

    @Test
    public void can_create_failure_result() {
        ConversionResult<String, Integer> result = ConversionResult.failure("abc",
                Objections.error("Error 1"),
                Objections.error("Error 2"));

        assertThat(result.uiValue)
                .isEqualTo("abc");

        assertThat(result.modelValue)
                .isNull();

        assertThat(result.isSuccessful())
                .isFalse();

        assertThat(result.objections)
                .isEqualTo(List.of(
                        Objections.error("Error 1"),
                        Objections.error("Error 2")));
    }
}