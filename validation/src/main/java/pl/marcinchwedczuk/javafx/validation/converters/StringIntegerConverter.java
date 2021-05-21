package pl.marcinchwedczuk.javafx.validation.converters;

import pl.marcinchwedczuk.javafx.validation.ConversionResult;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidatingValueConverter;

class StringIntegerConverter implements ValidatingValueConverter<String, Integer> {
    @Override
    public ConversionResult<String, Integer> toModelValue(String uiValue) {
        try {
            Integer converted = (uiValue == null) ? null : Integer.parseInt(uiValue, 10);
            return ConversionResult.success(uiValue, converted);
        } catch (NumberFormatException e) {
            return ConversionResult.failure(uiValue,
                    Objections.error("Cannot convert '%s' to a number.", uiValue));
        }
    }

    @Override
    public String toUiValue(Integer modelValue) {
        if (modelValue == null) {
            return null;
        }
        return Integer.toString(modelValue, 10);
    }
}
