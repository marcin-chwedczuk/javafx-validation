package pl.marcinchwedczuk.javafx.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConvertersTest {
    @Test
    public void test() {
        // Check accessible
        Converters.identityConverter().toModelValue("foo");
        ConvertersTest.class.toString();
        assertThat(1).isEqualTo(1);
    }
}