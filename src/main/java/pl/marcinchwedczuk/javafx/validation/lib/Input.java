package pl.marcinchwedczuk.javafx.validation.lib;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Input<UIV,MV> {
    private final SimpleObjectProperty<UIV> uiValueProperty = new SimpleObjectProperty<>(null);
    private final SimpleObjectProperty<MV> modelValueProperty = new SimpleObjectProperty<>(null);
    private final SimpleBooleanProperty validProperty = new SimpleBooleanProperty(false);

    // Sorted by priority
    // TODO: check automatically sorted by JavaFX
    private final SimpleListProperty<Objection> objectionsProperty =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    private boolean updating = false;
    private ValueDeduper<UIV> uiValueDeduper = new ValueDeduper<>();
    private ValueDeduper<MV> modelValueDeduper = new ValueDeduper<>();

    private final List<Validator<UIV>> uiValidators = new ArrayList<>();
    private final List<Validator<MV>> modelValidators = new ArrayList<>();
    private final ValidatingValueConverter<UIV, MV> converter;

    public Input(ValidatingValueConverter<UIV, MV> converter) {
        this.converter = converter;

        this.uiValueProperty.addListener(observable -> {
            UIV newValue = uiValueProperty.get();
            if (uiValueDeduper.checkNewValue(newValue)) {
                guardedPropagateUiValue(newValue);
            }
        });

        this.modelValueProperty.addListener(observable -> {
            MV newValue = modelValueProperty.getValue();
            if (modelValueDeduper.checkNewValue(newValue)) {
                guardedPropagateModelValue(newValue);
            }
        });
    }

    private void guardedPropagateUiValue(UIV newValue) {
        if (updating) {
            return;
        }

        updating = true;
        try {
            propagateUiValue(newValue);
        } finally {
            updating = false;
        }
    }

    public void reevaluateUiValue() {
        guardedPropagateUiValue(uiValueProperty.getValue());
    }

    private boolean propagateUiValue(UIV newValue) {
        List<Objection> objections = new ArrayList<>();

        ValidationResult<UIV> uiValidationResult = runValidators(uiValidators, newValue);
        objections.addAll(uiValidationResult.objections);
        if (!uiValidationResult.isValid()) {
            sortAndSetObjections(objections);
            validProperty.setValue(false);
            modelValueProperty.setValue(null);
            return true;
        }

        ConversionResult<UIV, MV> conversionResult = converter.toModelValue(newValue);
        objections.addAll(conversionResult.objections);
        if (!conversionResult.isSuccessful()) {
            sortAndSetObjections(objections);
            validProperty.setValue(false);
            modelValueProperty.setValue(null);
            return true;
        }

        MV newModelValue = conversionResult.modelValue;

        ValidationResult<MV> modelValidationResult = runValidators(modelValidators, newModelValue);
        objections.addAll(modelValidationResult.objections);
        if (!modelValidationResult.isValid()) {
            sortAndSetObjections(objections);
            validProperty.setValue(false);
            modelValueProperty.setValue(null);
        }

        sortAndSetObjections(objections);
        validProperty.setValue(true);
        modelValueProperty.setValue(newModelValue);
        return false;
    }

    private void guardedPropagateModelValue(MV newValue) {
        if (updating) {
            return;
        }

        updating = true;
        try {
            propagateModelValue(newValue);
        } finally {
            updating = false;
        }
    }

    private void propagateModelValue(MV newValue) {
        UIV newUiValue = converter.toUiValue(newValue);
        uiValueProperty.setValue(newUiValue);

        List<Objection> objections = new ArrayList<>();

        ValidationResult<MV> modelVR = runValidators(modelValidators, newValue);
        objections.addAll(modelVR.objections);
        if (!modelVR.isValid()) {
            sortAndSetObjections(modelVR.objections);
            validProperty.setValue(false);
            return;
        }

        ValidationResult<UIV> uiVR = runValidators(uiValidators, newUiValue);
        objections.addAll(uiVR.objections);
        if (!uiVR.isValid()) {
            sortAndSetObjections(uiVR.objections);
            validProperty.setValue(false);
            return;
        }

        sortAndSetObjections(objections);
        validProperty.setValue(true);
    }

    private static <T> ValidationResult<T>
    runValidators(List<Validator<T>> validators, T value) {
        if (validators.isEmpty()) {
            return ValidationResult.success(value);
        }

        List<ValidationResult<T>> validationResults = validators.stream()
                .map(v -> v.validate(value))
                .collect(toList());

        return ValidationResult.merge(validationResults);
    }

    private void sortAndSetObjections(List<Objection> objections) {
        objections.sort(Objections.compareBySeverityDesc());
        objectionsProperty.setAll(objections);
    }

    public Input<UIV,MV> withUiValidator(Validator<UIV> validator) {
        this.uiValidators.add(validator);
        return this;
    }

    @SafeVarargs
    public final Input<UIV,MV> withUiValidators(Validator<UIV>... validators) {
        for (Validator<UIV> v : validators) {
            withUiValidator(v);
        }
        return this;
    }

    public Input<UIV,MV> withModelValidator(Validator<MV> validator) {
        this.modelValidators.add(validator);
        return this;
    }

    public SimpleObjectProperty<UIV> uiValueProperty() {
        return uiValueProperty;
    }
    public UIV getUiValue() {
        return uiValueProperty().getValue();
    }
    public void setUiValue(UIV value) {
        uiValueProperty().setValue(value);
    }

    public SimpleObjectProperty<MV> modelValueProperty() {
        return modelValueProperty;
    }
    public MV getModelValue() {
        return modelValueProperty().getValue();
    }
    public void setModelValue(MV value) {
        modelValueProperty().setValue(value);
    }

    public ReadOnlyBooleanProperty validProperty() {
        return validProperty;
    }
    public boolean isValid() { return validProperty.get(); }

    public ReadOnlyListProperty<Objection> objectionsProperty() {
        return objectionsProperty;
    }
    public ObservableList<Objection> getObjections() {
        return objectionsProperty().get();
    }
}
