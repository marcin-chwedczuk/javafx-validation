package pl.marcinchwedczuk.javafx.validation.impl;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TemplateTest {
    @Test
    void works() {
        Template template = new Template(
                "#{value} must be between #{min} and #{max}.");

        String result = template.render(123, Map.of(
                "min", 1,
                "max", 999
        ));

        assertThat(result)
                .isEqualTo("123 must be between 1 and 999.");
    }

    // TODO: Test for observalbes
}