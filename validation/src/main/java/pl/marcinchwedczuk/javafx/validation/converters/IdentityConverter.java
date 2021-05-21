package pl.marcinchwedczuk.javafx.validation.converters;

import pl.marcinchwedczuk.javafx.validation.ConversionResult;
import pl.marcinchwedczuk.javafx.validation.ValidatingValueConverter;

import java.util.List;

class IdentityConverter<A> implements ValidatingValueConverter<A, A> {
    @Override
    public ConversionResult<A, A> toModelValue(A uiValue) {
        return new ConversionResult<>(uiValue, uiValue, List.of());
    }

    @Override
    public A toUiValue(A modelValue) {
        return modelValue;
    }
}
