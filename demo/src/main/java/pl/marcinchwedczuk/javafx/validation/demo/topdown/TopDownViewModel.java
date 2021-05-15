package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import pl.marcinchwedczuk.javafx.validation.Converters;
import pl.marcinchwedczuk.javafx.validation.Input;
import pl.marcinchwedczuk.javafx.validation.ValidationGroup;
import pl.marcinchwedczuk.javafx.validation.ObjectValidators;
import pl.marcinchwedczuk.javafx.validation.StringValidators;
import pl.marcinchwedczuk.javafx.validation.demo.mainwindow.UiService;

import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.ObjectValidators.required;
import static pl.marcinchwedczuk.javafx.validation.StringValidators.nonBlank;

public class TopDownViewModel {
    private final UiService uiService;

    public final ReadOnlyListProperty<Country> countries = new SimpleListProperty<>(this, "countries",
            FXCollections.observableArrayList(Country.values()));

    public final Input<Country, Country> selectedCountry = new Input<Country, Country>(Converters.identityConverter())
            .withUiValidators(ObjectValidators.required());

    public final Input<String, PhoneNumber> mobilePhone = new Input<String, PhoneNumber>(PhoneNumber.converter())
            .withUiValidators(StringValidators.nonBlank("Phone number is required."))
            .withModelValidator(CustomValidators.phoneHasCountryPrefix(selectedCountry.modelValueProperty()));

    public final Input<String, FaxNumber> faxNumber = new Input<String, FaxNumber>(FaxNumber.converter())
            .withUiValidators(StringValidators.nonBlank("Fax number is required."))
            .withModelValidator(CustomValidators.faxHasCountryPrefix(selectedCountry.modelValueProperty()));

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

        selectedCountry.reset();
        mobilePhone.reset();
        faxNumber.reset();
    }

    public ReadOnlyBooleanProperty showErrorBannerProperty() {
        return showErrorBanner;
    }
}
