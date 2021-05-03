package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.List;

public class ConversionResult<UIV, MV> {
    public static <UIV, MV>
    ConversionResult<UIV, MV> success(UIV uiValue, MV modelValue) {
        return new ConversionResult<>(modelValue, uiValue, List.of());
    }

    public static <UIV, MV>
    ConversionResult<UIV, MV> failure(UIV uiValue, Objection... objections) {
        if (!Objections.containsError(List.of(objections))) {
            throw new IllegalArgumentException("No error was reported!");
        }
        return new ConversionResult<>(null, uiValue, List.of(objections));
    }

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
