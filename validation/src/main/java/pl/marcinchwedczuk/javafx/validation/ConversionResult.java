package pl.marcinchwedczuk.javafx.validation;

public class ConversionResult<UIV, MV> {
    public static <UIV, MV>
    ConversionResult<UIV, MV> success(UIV uiValue, MV modelValue) {
        return new ConversionResult<>(uiValue, modelValue, ObjectionsList.EMPTY);
    }

    public static <UIV, MV>
    ConversionResult<UIV, MV> failure(UIV uiValue, Objection first, Objection... rest) {
        ObjectionsList objections = ObjectionsList.of(first, rest);

        if (!objections.containsError()) {
            throw new IllegalArgumentException("No error was reported!");
        }

        return new ConversionResult<>(uiValue, null, objections);
    }

    public final MV modelValue;
    public final UIV uiValue;
    public final ObjectionsList objections;

    public ConversionResult(UIV uiValue, MV modelValue,
                            ObjectionsList objections) {
        this.modelValue = modelValue;
        this.uiValue = uiValue;
        this.objections = objections;
    }

    public boolean isSuccessful() {
        return !objections.containsError();
    }
}
