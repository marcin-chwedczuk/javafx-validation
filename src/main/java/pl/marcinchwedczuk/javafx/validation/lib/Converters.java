package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.List;

public class Converters {
    private Converters() { }

    public static <A> ValidatingValueConverter<A,A> identityConverter() {
        return new ValidatingValueConverter<>() {
            @Override
            public ConversionResult<A, A> toModelValue(A uiValue) {
                return new ConversionResult<>(uiValue, uiValue, List.of());
            }

            @Override
            public A toUiValue(A modelValue) {
                return modelValue;
            }
        };
    }
}
