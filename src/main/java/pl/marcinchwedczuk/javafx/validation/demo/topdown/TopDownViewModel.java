package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.marcinchwedczuk.javafx.validation.demo.UiService;
import pl.marcinchwedczuk.javafx.validation.lib.*;

import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.lib.StringValidators.nonBlank;

public class TopDownViewModel {
    private final UiService uiService;

    public final ReadOnlyListProperty<Country> countries = new SimpleListProperty<>(this, "countries",
            FXCollections.observableArrayList(Country.values()));

    public final Input<Country, Country> selectedCountry = new Input<Country, Country>(Converters.identityConverter())
            .withUiValidators(ObjectValidators.required());

    public final Input<String, PhoneNumber> mobilePhone = new Input<String, PhoneNumber>(PhoneNumber.converter())
            .withUiValidators(nonBlank("Phone number is required."));

    public final Input<String, FaxNumber> faxNumber = new Input<String, FaxNumber>(FaxNumber.converter())
            .withUiValidators(nonBlank("Fax number is required."));

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
