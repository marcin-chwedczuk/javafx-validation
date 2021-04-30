package pl.marcinchwedczuk.javafx.validation.extra;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pl.marcinchwedczuk.javafx.validation.lib.Input;

public class UiBindings {
    private UiBindings() { }

    public static <T> void biBind(TextField textControl, Input<String, T> modelInput) {
        biBind(textControl.textProperty(), modelInput);
        triggerOnFocusLost(textControl.focusedProperty(), modelInput);
    }

    public static <T> void biBind(Property<T> uiProperty,
                                  Input<T, ?> modelInput)
    {
        uiProperty.bindBidirectional(modelInput.uiValueProperty());
    }

    public static <T> void triggerOnFocusLost(
            ReadOnlyBooleanProperty focusProperty,
            Input<T, ?> modelInput)
    {
        focusProperty.addListener((InvalidationListener)observable -> {
            // Handle only focus lost
            if (!focusProperty.getValue()) {
                modelInput.reevaluateUiValue();
            }
        });
    }
}
