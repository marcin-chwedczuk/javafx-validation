package pl.marcinchwedczuk.javafx.validation.extra;

import javafx.beans.property.Property;
import pl.marcinchwedczuk.javafx.validation.lib.Input;

public class UiBindings {
    private UiBindings() { }

    public static <T> void biBind(Property<T> uiProperty,
                                  Input<T, ?> modelEntry)
    {
        uiProperty.bindBidirectional(modelEntry.uiValueProperty());
    }
}
