package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import pl.marcinchwedczuk.javafx.validation.demo.UiService;
import pl.marcinchwedczuk.javafx.validation.lib.*;

import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.demo.topdown.CustomValidators.faxHasCountryPrefix;
import static pl.marcinchwedczuk.javafx.validation.demo.topdown.CustomValidators.phoneHasCountryPrefix;
import static pl.marcinchwedczuk.javafx.validation.lib.ObjectValidators.required;
import static pl.marcinchwedczuk.javafx.validation.lib.StringValidators.nonBlank;

public class TopDownViewModel {
    private final UiService uiService;

    public final ReadOnlyListProperty<Country> countries = new SimpleListProperty<>(this, "countries",
            FXCollections.observableArrayList(Country.values()));

    public final Input<Country, Country> selectedCountry = new Input<Country, Country>(Converters.identityConverter())
            .withUiValidators(required());

    public final Input<String, PhoneNumber> mobilePhone = new Input<String, PhoneNumber>(PhoneNumber.converter())
            .withUiValidators(nonBlank("Phone number is required."))
            .withModelValidator(phoneHasCountryPrefix(selectedCountry.modelValueProperty()));

    public final Input<String, FaxNumber> faxNumber = new Input<String, FaxNumber>(FaxNumber.converter())
            .withUiValidators(nonBlank("Fax number is required."))
            .withModelValidator(faxHasCountryPrefix(selectedCountry.modelValueProperty()));

    private final ValidationGroup topDownDemoForm = new ValidationGroup(selectedCountry, mobilePhone, faxNumber);
    private final BooleanProperty showErrorBanner = new SimpleBooleanProperty(false);

    public TopDownViewModel(UiService uiService) {
        this.uiService = Objects.requireNonNull(uiService);
    }

    public void validate() {
        if (!topDownDemoForm.validate()) {
            showErrorBanner.set(true);
            return;
        }

        showErrorBanner.set(false);
        uiService.infoDialog(
                "Mobile Phone: " + mobilePhone.getModelValue().toString() + "\n" +
                "Fax: " + faxNumber.getModelValue().toString() + "\n" +
                "Country: " + selectedCountry.getModelValue());
    }

    public ReadOnlyBooleanProperty showErrorBannerProperty() {
        return showErrorBanner;
    }
}
