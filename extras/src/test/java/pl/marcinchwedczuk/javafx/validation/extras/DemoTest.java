package pl.marcinchwedczuk.javafx.validation.extras;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DemoTest {
    @Test
    public void test() {
        // Check accessible
        UiBindings.class.toString();

        assertThat(1).isEqualTo(1);
    }
}