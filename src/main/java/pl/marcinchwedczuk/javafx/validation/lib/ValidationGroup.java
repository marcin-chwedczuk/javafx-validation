package pl.marcinchwedczuk.javafx.validation.lib;

import javafx.beans.Observable;
import javafx.beans.property.*;
import pl.marcinchwedczuk.javafx.validation.demo.UiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.lib.ValidationState.NOT_RUN;
import static pl.marcinchwedczuk.javafx.validation.lib.ValidationState.VALID;

public class ValidationGroup {
    private final List<Input<?,?>> inputs;
    private final ObjectProperty<ValidationState> validationState = new SimpleObjectProperty<>(this, "validationState", null);

    public ValidationGroup(Input<?,?>... inputs) {
        Objects.requireNonNull(inputs);
        this.inputs = new ArrayList<>(Arrays.asList(inputs));

        for (Input<?,?> input: inputs) {
            input.validationStateProperty().addListener(this::reevaluate);
        }

        reevaluate();
    }

    private void reevaluate(Observable unused) {
        reevaluate();
    }

    private void reevaluate() {
        ValidationState acc = VALID;

        for (Input<?,?> input: inputs) {
            acc = ValidationState.combine(acc, input.getValidationState());
        }

        if (acc != validationState.getValue()) {
            validationState.setValue(acc);
        }
    }

    public boolean validate() {
        for (Input<?,?> input: inputs) {
            // TODO: change to performValidation
            input.reevaluateUiValue();
        }

        assert (getValidationState() != NOT_RUN);
        return (getValidationState() == VALID);
    }

    public ValidationState getValidationState() {
        return validationState.get();
    }
    public ReadOnlyObjectProperty<ValidationState> validationStateProperty() {
        return validationState;
    }
}
