package pl.marcinchwedczuk.javafx.validation;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ObjectionTest extends BaseUnitTest {

    @Test
    public void verify_equals_and_hashCode() {
        EqualsVerifier
                .forClass(Objection.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void toString_works() {
        Objection objection =
                new Objection("Message", ObjectionSeverity.ERROR);

        String result = objection.toString();

        assertThat(result)
                .isEqualTo("Objection { userMessage: \"Message\", severity: ERROR }");
    }
}