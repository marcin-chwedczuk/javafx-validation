package pl.marcinchwedczuk.javafx.validation.lib;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Property<UIV,MV> {
    private final SimpleObjectProperty<UIV> uiValueProperty = new SimpleObjectProperty<>(null);
    private final SimpleObjectProperty<MV> modelValueProperty = new SimpleObjectProperty<>(null);
    private final SimpleBooleanProperty validProperty = new SimpleBooleanProperty(false);

    // Sorted by priority
    // TODO: check automatically sorted by JavaFX
    private final SimpleListProperty<Objection> validationErrorsProperty =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    private boolean updating = false;
    private UIV previousUiValue = null;
    private MV previousModelValue = null;

    private final List<Validator<UIV>> uiValidators = new ArrayList<>();
    private final List<Validator<MV>> modelValidators = new ArrayList<>();
    private final ValidatingValueConverter<UIV, MV> converter;

    public Property(ValidatingValueConverter<UIV, MV> converter) {
        this.converter = converter;

        this.uiValueProperty.addListener(observable -> {
            UIV newValue = uiValueProperty.get();
            if (newValue == previousUiValue) {
                return;
            }

            propagateUiValue(newValue);
            previousUiValue = newValue;
        });

        this.modelValueProperty.addListener(observable -> {
            MV newValue = modelValueProperty.getValue();
            if (newValue == previousModelValue) {
                return;
            }

            propagateModelValue(newValue);
            previousModelValue = newValue;
        });
    }

    private void propagateUiValue(UIV newValue) {
        if (updating) {
            return;
        }

        updating = true;
        try {
            List<Objection> nonErrorObjections = new ArrayList<>();

            ValidationResult<UIV> uiValidationResult = runValidators(uiValidators, newValue);
            if (!uiValidationResult.isValid()) {
                validationErrorsProperty.setAll(uiValidationResult.objections);
                validProperty.setValue(false);
                modelValueProperty.setValue(null);
                return;
            }
            nonErrorObjections.addAll(uiValidationResult.objections);

            ConversionResult<UIV, MV> conversionResult = converter.toModelValue(newValue);
            if (!conversionResult.isSuccessful()) {
                validationErrorsProperty.setAll(conversionResult.objections);
                validProperty.setValue(false);
                modelValueProperty.setValue(null);
                return;
            }
            nonErrorObjections.addAll(conversionResult.objections);

            MV newModelValue = conversionResult.modelValue;

            ValidationResult<MV> modelValidationResult = runValidators(modelValidators, newModelValue);
            if (!modelValidationResult.isValid()) {
                validationErrorsProperty.setAll(modelValidationResult.objections);
                validProperty.setValue(false);
                modelValueProperty.setValue(null);
            }
            nonErrorObjections.addAll(modelValidationResult.objections);

            nonErrorObjections.sort(Objections.compareBySeverity());
            validationErrorsProperty.setAll(nonErrorObjections);
            validProperty.setValue(true);
            modelValueProperty.setValue(newModelValue);
        } finally {
            updating = false;
        }
    }


    private void propagateModelValue(MV newValue) {
        if (updating) {
            return;
        }

        updating = true;
        try {
            UIV newUiValue = converter.toUiValue(newValue);
            uiValueProperty.setValue(newUiValue);

            List<Objection> nonErrorObjections = new ArrayList<>();

            // TODO: Merge warning from prev stages with current errors
            // TODO: Sort before adding - check auto sorted list setErrors(...) method
            ValidationResult<MV> modelValidationResult = runValidators(modelValidators, newValue);
            if (!modelValidationResult.isValid()) {
                validationErrorsProperty.setAll(modelValidationResult.objections);
                validProperty.setValue(false);
                return;
            }
            nonErrorObjections.addAll(modelValidationResult.objections);

            ValidationResult<UIV> uiValidationResult = runValidators(uiValidators, newUiValue);
            if (!uiValidationResult.isValid()) {
                validationErrorsProperty.setAll(uiValidationResult.objections);
                validProperty.setValue(false);
                return;
            }
            nonErrorObjections.addAll(uiValidationResult.objections);

            nonErrorObjections.sort(Objections.compareBySeverity());
            validationErrorsProperty.setAll(nonErrorObjections);
            validProperty.setValue(true);

        } finally {
            updating = false;
        }
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

    public Property<UIV,MV> withUiValidator(Validator<UIV> validator) {
        this.uiValidators.add(validator);
        return this;
    }

    @SafeVarargs
    public final Property<UIV,MV> withUiValidators(Validator<UIV>... validators) {
        for (Validator<UIV> v : validators) {
            withUiValidator(v);
        }
        return this;
    }

    public Property<UIV,MV> withModelValidator(Validator<MV> validator) {
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


    public ReadOnlyListProperty<Objection> validationErrorsProperty() {
        return validationErrorsProperty;
    }
    public ObservableList<Objection> getValidationErrors() {
        return validationErrorsProperty().get();
    }
}