package pl.marcinchwedczuk.javafx.validation.demo.range;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import pl.marcinchwedczuk.javafx.validation.*;
import pl.marcinchwedczuk.javafx.validation.converters.Converters;
import pl.marcinchwedczuk.javafx.validation.demo.mainwindow.UiService;
import pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators;
import pl.marcinchwedczuk.javafx.validation.validators.StringValidators;

import java.util.Objects;

public class NumberRangeViewModel {
    private final UiService uiService;

    public final Input<String, Integer> from =
            new Input<String, Integer>(Converters.stringIntegerConverter())
                    .withUiValidators(StringValidators.nonBlank());

    public final Input<String, Integer> to =
            new Input<String, Integer>(Converters.stringIntegerConverter())
                    .withUiValidators(StringValidators.nonBlank())
                    .withModelValidator(IntegerValidators.validRangeWithStart(from.modelValueProperty(), IntegerValidators.RangeOptions.DISALLOW_EMPTY_RANGE));

    private final ValidationGroup rangeForm = new ValidationGroup(from, to);
    private final BooleanProperty showErrorBanner = new SimpleBooleanProperty(false);

    public NumberRangeViewModel(UiService uiService) {
        this.uiService = Objects.requireNonNull(uiService);
    }

    public void validate() {
        if (!rangeForm.validate()) {
            showErrorBanner.set(true);
            return;
        }

        uiService.infoDialog(String.format(
                "Valid range %d - %d.", from.getModelValue(), to.getModelValue()));

        showErrorBanner.set(false);
        from.reset();
        to.reset();
    }

    public ReadOnlyBooleanProperty showErrorBannerProperty() {
        return showErrorBanner;
    }
}
