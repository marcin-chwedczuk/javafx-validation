package pl.marcinchwedczuk.javafx.validation.extras;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.*;
import pl.marcinchwedczuk.javafx.validation.Input;

import java.util.function.BiFunction;
import java.util.function.Function;

public class UiBindings {
    private UiBindings() {
    }

    public static <T> void biBind(TextInputControl textControl, Input<String, T> modelInput) {
        biBind(textControl.textProperty(), modelInput);
        triggerOnFocusLost(textControl.focusedProperty(), modelInput);
    }

    public static <T> void biBind(ChoiceBox<T> choiceBox, Input<T, ?> modelInput) {
        biBind(choiceBox.valueProperty(), modelInput);
    }

    public static void biBind(CheckBox choiceBox, Input<Boolean, ?> modelInput) {
        biBind(choiceBox.selectedProperty(), modelInput);
    }

    public static <T> void biBind(Spinner<T> spinner, Input<T, ?> modelInput) {
        // TODO: Will not work when factory changes
        biBind(spinner.getValueFactory().valueProperty(), modelInput);
    }

    public static <T> void biBind(Property<T> uiProperty,
                                  Input<T, ?> modelInput) {
        uiProperty.bindBidirectional(modelInput.uiValueProperty());
    }

    public static <A> DoubleBinding map(ReadOnlyProperty<A> input, Function<A, Double> mapping) {
        return Bindings.createDoubleBinding(() -> mapping.apply(input.getValue()), input);
    }

    public static <A, B> StringBinding map2(ReadOnlyProperty<A> inputA, ReadOnlyProperty<B> inputB, BiFunction<A, B, String> mapping) {
        return Bindings.createStringBinding(
                () -> mapping.apply(inputA.getValue(), inputB.getValue()),
                inputA, inputB);
    }

    public static <T> void triggerOnFocusLost(
            ReadOnlyBooleanProperty focusProperty,
            Input<T, ?> modelInput) {
        focusProperty.addListener((InvalidationListener) observable -> {
            // Handle only focus lost
            if (!focusProperty.getValue()) {
                modelInput.reevaluateUiValue();
            }
        });
    }
}
