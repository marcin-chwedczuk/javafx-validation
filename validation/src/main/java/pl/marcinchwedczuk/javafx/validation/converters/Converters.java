package pl.marcinchwedczuk.javafx.validation.converters;

import pl.marcinchwedczuk.javafx.validation.ValueConverter;

public class Converters {
    private Converters() { }

    public static <A> ValueConverter<A, A> identityConverter() {
        return new IdentityConverter<>();
    }

    public static ValueConverter<String, Integer> stringIntegerConverter() {
        return new StringIntegerConverter();
    }
}
