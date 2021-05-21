package pl.marcinchwedczuk.javafx.validation.impl;

import org.junit.jupiter.api.Test;
import pl.marcinchwedczuk.javafx.validation.impl.ValueDeduper;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueDeduperTest {

    @Test
    void value_deduper_works() {
        ValueDeduper<Object> deduper = new ValueDeduper<>();

        assertThat(deduper.checkNewValue(null))
                .isTrue();

        assertThat(deduper.checkNewValue(null))
                .isFalse();

        assertThat(deduper.checkNewValue("foo"))
                .isTrue();

        assertThat(deduper.checkNewValue("foo"))
                .isFalse();

        assertThat(deduper.checkNewValue(null))
                .isTrue();

        assertThat(deduper.checkNewValue("foo"))
                .isTrue();
    }
}