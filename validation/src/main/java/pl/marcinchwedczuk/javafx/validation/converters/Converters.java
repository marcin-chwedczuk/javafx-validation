package pl.marcinchwedczuk.javafx.validation.converters;

import pl.marcinchwedczuk.javafx.validation.ValidatingValueConverter;
import pl.marcinchwedczuk.javafx.validation.converters.IdentityConverter;
import pl.marcinchwedczuk.javafx.validation.converters.StringIntegerConverter;

import java.util.List;

public class Converters {
    private Converters() { }

    public static <A> ValidatingValueConverter<A, A> identityConverter() {
        return new IdentityConverter<>();
    }

    public static ValidatingValueConverter<String, Integer> stringIntegerConverter() {
        return new StringIntegerConverter();
    }
}
