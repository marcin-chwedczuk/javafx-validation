package pl.marcinchwedczuk.javafx.validation.lib;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ValidationGroup {
    private final List<Input<?,?>> inputs;
    private final BooleanProperty valid = new SimpleBooleanProperty(this, "isValid", false);

    public ValidationGroup(Input<?,?>... inputs) {
        Objects.requireNonNull(inputs);
        this.inputs = new ArrayList<>(Arrays.asList(inputs));

        for (Input<?,?> input: inputs) {
            input.validProperty().addListener(this::reevaluate);
        }

        reevaluate();
    }

    private void reevaluate(Observable unused) {
        reevaluate();
    }

    private void reevaluate() {
        boolean newIsValid = true;

        for (Input<?,?> input: inputs) {
            newIsValid &= input.isValid();
        }

        if (newIsValid != valid.getValue()) {
            valid.setValue(newIsValid);
        }
    }

    public boolean isValid() {
        return valid.get();
    }

    public ReadOnlyBooleanProperty validProperty() {
        return valid;
    }
}
