package pl.marcinchwedczuk.javafx.validation;

public interface ValidatingValueConverter<UIV, MV> {
    ConversionResult<UIV, MV> toModelValue(UIV uiValue);

    UIV toUiValue(MV modelValue);
}
