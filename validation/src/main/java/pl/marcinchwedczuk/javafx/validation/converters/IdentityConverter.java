package pl.marcinchwedczuk.javafx.validation.converters;

import pl.marcinchwedczuk.javafx.validation.ConversionResult;
import pl.marcinchwedczuk.javafx.validation.ObjectionsList;
import pl.marcinchwedczuk.javafx.validation.ValueConverter;

import java.util.List;

class IdentityConverter<A> implements ValueConverter<A, A> {
    @Override
    public ConversionResult<A, A> toModelValue(A uiValue) {
        return new ConversionResult<>(uiValue, uiValue, ObjectionsList.EMPTY);
    }

    @Override
    public A toUiValue(A modelValue) {
        return modelValue;
    }
}
