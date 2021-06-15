package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.Observable;
import javafx.beans.property.*;
import pl.marcinchwedczuk.javafx.validation.impl.InvalidationListenerHandle;
import pl.marcinchwedczuk.javafx.validation.impl.ValueDeduper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Validation workflow:
 * - Validated only on user action.
 * - checkValid() must be called on ValidationGroup to display errors.
 * - no isValid real-time property.
 *
 * @param <UIV>
 * @param <MV>
 */
// TODO: Change name to Field
// TODO: Add ReadOnlyField class
public class Input<UIV, MV> {
    private final ObjectProperty<UIV> uiValue = new SimpleObjectProperty<>(this, "uiValue", null);
    private final ObjectProperty<MV> modelValue = new SimpleObjectProperty<>(this, "modelValue", null);

    private final ObjectProperty<ValidationState> validationState =
            // Ctor runs validation that sets proper value for this field.
            new SimpleObjectProperty<>(this, "validationState", null);

    // Sorted by priority
    // TODO: check automatically sorted by JavaFX
    private final ObjectProperty<ObjectionsList> objectionsProperty =
            new SimpleObjectProperty<>(this, "objections", ObjectionsList.EMPTY);

    private boolean updating = false;
    private final ValueDeduper<UIV> uiValueDeduper = new ValueDeduper<>();
    private final ValueDeduper<MV> modelValueDeduper = new ValueDeduper<>();

    private final List<Validator<? super UIV>> uiValidators = new ArrayList<>();
    private final List<Validator<? super MV>> modelValidators = new ArrayList<>();
    private final ValueConverter<UIV, MV> converter;

    private final BooleanProperty pristine = new SimpleBooleanProperty(this, "pristine", true);

    private final InvalidationListenerHandle validatorDependencyChangedLH =
            new InvalidationListenerHandle(this::onValidatorDependencyChanged);

    public Input(ValueConverter<UIV, MV> converter) {
        this.converter = Objects.requireNonNull(converter);

        this.uiValue.addListener(observable -> {
            UIV newValue = uiValue.get();
            handleUiValueMaybeChanged(newValue);
        });

        this.modelValue.addListener(observable -> {
            MV newValue = modelValue.getValue();
            if (modelValueDeduper.checkNewValue(newValue)) {
                guardedPropagateModelValue(newValue);
            }
        });
    }

    private void handleUiValueMaybeChanged(UIV newValue) {
        if (uiValueDeduper.checkNewValue(newValue)) {
            guardedPropagateUiValue(newValue);
        }
    }

    public void reevaluateUiValue() {
        pristine.set(false);
        guardedPropagateUiValue(uiValue.getValue());
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

    private void propagateUiValue(UIV newValue) {
        pristine.setValue(false);

        List<Objection> objections = new ArrayList<>();

        ValidationResult<UIV> uiValidationResult = runValidators(uiValidators, newValue);
        objections.addAll(uiValidationResult.objections.asList());
        if (!uiValidationResult.isValid()) {
            sortAndSetObjections(objections);
            validationState.setValue(ValidationState.INVALID);
            modelValue.setValue(null);
            return;
        }

        ConversionResult<UIV, MV> conversionResult = converter.toModelValue(newValue);
        objections.addAll(conversionResult.objections.asList());
        if (!conversionResult.isSuccessful()) {
            sortAndSetObjections(objections);
            validationState.setValue(ValidationState.INVALID);
            modelValue.setValue(null);
            return;
        }

        MV newModelValue = conversionResult.modelValue;

        ValidationResult<MV> modelValidationResult = runValidators(modelValidators, newModelValue);
        objections.addAll(modelValidationResult.objections.asList());
        if (!modelValidationResult.isValid()) {
            sortAndSetObjections(objections);
            validationState.setValue(ValidationState.INVALID);
            modelValue.setValue(null);
            return;
        }

        sortAndSetObjections(objections);
        validationState.setValue(ValidationState.VALID);
        modelValue.setValue(newModelValue);
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
        uiValue.setValue(newUiValue);

        List<Objection> objections = new ArrayList<>();

        ValidationResult<MV> modelVR = runValidators(modelValidators, newValue);
        objections.addAll(modelVR.objections.asList());
        if (!modelVR.isValid()) {
            sortAndSetObjections(modelVR.objections.asList());
            validationState.setValue(ValidationState.INVALID);
            return;
        }

        ValidationResult<UIV> uiVR = runValidators(uiValidators, newUiValue);
        objections.addAll(uiVR.objections.asList());
        if (!uiVR.isValid()) {
            sortAndSetObjections(uiVR.objections.asList());
            validationState.setValue(ValidationState.INVALID);
            return;
        }

        sortAndSetObjections(objections);
        validationState.setValue(ValidationState.VALID);
    }

    private static <T> ValidationResult<T>
    runValidators(List<Validator<? super T>> validators, T value) {
        if (validators.isEmpty()) {
            return ValidationResult.success(value);
        }

        List<ValidationResult<T>> validationResults = validators.stream()
                .map(v -> v.validate(value))
                .collect(toList());

        return ValidationResult.merge(validationResults);
    }

    private void sortAndSetObjections(List<Objection> objections) {
        objectionsProperty.set(
                new ObjectionsList(objections).copySortedBySeverity()
        );
    }

    public Input<UIV, MV> withUiValidator(Validator<? super UIV> validator) {
        this.uiValidators.add(validator);
        addDependenciesListener(validator);
        return this;
    }

    @SafeVarargs
    public final Input<UIV, MV> withUiValidators(Validator<? super UIV>... validators) {
        for (Validator<? super UIV> v : validators) {
            withUiValidator(v);
        }
        return this;
    }

    @SafeVarargs
    public final Input<UIV, MV> withUiValidators(CustomizableValidator<? super UIV>... validatorBuilders) {
        for (CustomizableValidator<? super UIV> builder : validatorBuilders) {
            withUiValidator(builder.create());
        }
        return this;
    }

    public Input<UIV, MV> withModelValidator(CustomizableValidator<? super MV> builder) {
        return withModelValidator(builder.create());
    }

    public Input<UIV, MV> withModelValidator(Validator<? super MV> validator) {
        this.modelValidators.add(validator);
        addDependenciesListener(validator);
        return this;
    }

    public Input<UIV, MV> withInitialValue(MV value) {
        reset(value);
        return this;
    }

    private void addDependenciesListener(Validator<?> validator) {
        for (Observable dependency : validator.dependencies()) {
            validatorDependencyChangedLH.attachTo(dependency);
        }
    }

    private void onValidatorDependencyChanged() {
        // Do not run if the field is pristine
        if (isPristine()) return;
        // TODO: Do not unset pristine
        reevaluateUiValue();
    }

    public void reset() {
        reset(null);
    }

    public void reset(MV modelValue) {
        modelValueProperty().setValue(modelValue);

        // Clear validation
        pristine.set(true);
        validationState.set(ValidationState.NOT_RUN);
        objectionsProperty.set(ObjectionsList.EMPTY);
    }

    public ObjectProperty<UIV> uiValueProperty() {
        return uiValue;
    }

    public UIV getUiValue() {
        return uiValueProperty().getValue();
    }

    public void setUiValue(UIV value) {
        uiValueProperty().setValue(value);

        // Special case, since null is the UiValue property
        // default value.
        if (value == null) {
            handleUiValueMaybeChanged(null);
        }
    }

    public ObjectProperty<MV> modelValueProperty() {
        return modelValue;
    }

    public MV getModelValue() {
        return modelValueProperty().getValue();
    }

    public void setModelValue(MV value) {
        modelValueProperty().setValue(value);
    }

    public ValidationState getValidationState() {
        return validationState.get();
    }

    public ObjectProperty<ValidationState> validationStateProperty() {
        return validationState;
    }

    public ObjectionsList getObjections() {
        return objectionsProperty.get();
    }

    public ObjectProperty<ObjectionsList> objectionsProperty() {
        return objectionsProperty;
    }

    public boolean isPristine() {
        return pristine.get();
    }

    public BooleanProperty pristineProperty() {
        return pristine;
    }
}
