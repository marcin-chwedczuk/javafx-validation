package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.List;

public class ConversionResult<UIV, MV> {
    public final MV modelValue;
    public final UIV uiValue;
    public final List<ValidationError> validationErrors;

    public ConversionResult(MV modelValue,
                            UIV uiValue,
                            List<ValidationError> validationErrors) {
        this.modelValue = modelValue;
        this.uiValue = uiValue;
        this.validationErrors = validationErrors;
    }

    public boolean isSuccessful() {
        return validationErrors.isEmpty();
    }
}
