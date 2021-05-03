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

    public static ValidatingValueConverter<String, Integer> stringIntegerConverter() {
        return new ValidatingValueConverter<String, Integer>() {
            @Override
            public ConversionResult<String, Integer> toModelValue(String uiValue) {
                try {
                    return ConversionResult.success(
                            uiValue,
                            Integer.parseInt(uiValue, 10));
                } catch (NumberFormatException e) {
                    return ConversionResult.failure(uiValue,
                            Objections.error("Cannot convert '%s' to a number.", uiValue));
                }
            }

            @Override
            public String toUiValue(Integer modelValue) {
                if (modelValue == null) {
                    return "";
                }
                return Integer.toString(modelValue, 10);
            }
        };
    }
}
