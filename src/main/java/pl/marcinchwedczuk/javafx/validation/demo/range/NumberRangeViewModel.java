package pl.marcinchwedczuk.javafx.validation.demo.range;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import pl.marcinchwedczuk.javafx.validation.demo.UiService;
import pl.marcinchwedczuk.javafx.validation.lib.*;

import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.lib.Converters.stringIntegerConverter;
import static pl.marcinchwedczuk.javafx.validation.lib.IntegerValidators.RangeOptions.NON_EMPTY_RANGE;
import static pl.marcinchwedczuk.javafx.validation.lib.IntegerValidators.validRangeWithStart;
import static pl.marcinchwedczuk.javafx.validation.lib.StringValidators.required;

public class NumberRangeViewModel {
    private final UiService uiService;

    public final Input<String, Integer> from =
            new Input<String, Integer>(stringIntegerConverter())
                    .withUiValidators(required());

    public final Input<String, Integer> to =
            new Input<String, Integer>(stringIntegerConverter())
                .withUiValidators(required())
                .withModelValidator(validRangeWithStart(from.modelValueProperty(), NON_EMPTY_RANGE));

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
