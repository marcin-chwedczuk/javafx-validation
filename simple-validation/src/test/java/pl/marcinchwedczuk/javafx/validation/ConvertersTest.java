package pl.marcinchwedczuk.javafx.validation;

import org.junit.jupiter.api.Test;
import pl.marcinchwedczuk.javafx.validation.Converters;

import static org.assertj.core.api.Assertions.*;

public class ConvertersTest {
    @Test
    public void test() {
        // Check accessible
        Converters.identityConverter().toModelValue("foo");

        assertThat(1).isEqualTo(1);
    }
}