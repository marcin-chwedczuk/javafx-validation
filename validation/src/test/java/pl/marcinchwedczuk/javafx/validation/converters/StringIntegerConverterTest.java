package pl.marcinchwedczuk.javafx.validation.converters;

import org.junit.jupiter.api.Test;
import pl.marcinchwedczuk.javafx.validation.Objections;

import static org.junit.jupiter.api.Assertions.*;

class StringIntegerConverterTest extends BaseConverterTest {

    @Test
    public void converter_works() {
        StringIntegerConverter converter = new StringIntegerConverter();

        assertConvertsBetween(converter, "0", 0);
        assertConvertsBetween(converter, "999", 999);
        assertConvertsBetween(converter, "-100", -100);

        assertConvertsBetween(converter, null, null);

        assertFailsWithObjections(converter, "abc",
                Objections.error("Cannot convert 'abc' to a number."));
    }


}