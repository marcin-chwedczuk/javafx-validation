package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import pl.marcinchwedczuk.javafx.validation.demo.UiService;
import pl.marcinchwedczuk.javafx.validation.lib.Input;
import pl.marcinchwedczuk.javafx.validation.lib.ValidationGroup;

import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.lib.Converters.stringIntegerConverter;
import static pl.marcinchwedczuk.javafx.validation.lib.IntegerValidators.RangeOptions.NON_EMPTY_RANGE;
import static pl.marcinchwedczuk.javafx.validation.lib.IntegerValidators.validRangeWithStart;
import static pl.marcinchwedczuk.javafx.validation.lib.StringValidators.required;

public class TopDownViewModel {
    private final UiService uiService;

    private final ValidationGroup rangeForm = new ValidationGroup();
    private final BooleanProperty showErrorBanner = new SimpleBooleanProperty(false);

    public TopDownViewModel(UiService uiService) {
        this.uiService = Objects.requireNonNull(uiService);
    }

    public void validate() {
        if (!rangeForm.validate()) {
            showErrorBanner.set(true);
            return;
        }

        uiService.infoDialog("Works!");
    }

    public ReadOnlyBooleanProperty showErrorBannerProperty() {
        return showErrorBanner;
    }
}
