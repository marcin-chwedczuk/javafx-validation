package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.List;

public class ConversionResult<UIV, MV> {
    public final MV modelValue;
    public final UIV uiValue;
    public final List<Objection> objections;

    public ConversionResult(MV modelValue,
                            UIV uiValue,
                            List<Objection> objections) {
        this.modelValue = modelValue;
        this.uiValue = uiValue;
        this.objections = objections;
    }

    public boolean isSuccessful() {
        return !Objections.containsError(objections);
    }
}
