package pl.marcinchwedczuk.javafx.validation.converters;

import org.junit.jupiter.api.Test;
import pl.marcinchwedczuk.javafx.validation.Converters;
import pl.marcinchwedczuk.javafx.validation.ValidatingValueConverter;

import static org.junit.jupiter.api.Assertions.*;

class IdentityConverterTest extends BaseConverterTest {
    @Test
    public void identity_converter_works() {
        Object someObj = new Object();

        IdentityConverter<Object> identityConverter = new IdentityConverter<>();

        assertConvertsBetween(identityConverter, null, null);
        assertConvertsBetween(identityConverter, someObj, someObj);
    }
}