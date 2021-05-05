package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.List;
import java.util.Objects;

public class CustomValidators {
    private CustomValidators() { }

    public static Validator<PhoneNumber> phoneHasCountryPrefix(ObjectProperty<Country> countryProperty) {
        Objects.requireNonNull(countryProperty);

        return new Validator<>() {
            @Override
            public <VALIDATED extends PhoneNumber>
            ValidationResult<VALIDATED> validate(VALIDATED value) {
                Country country = countryProperty.get();

                boolean isValid =
                        (country != null) &&
                        (value != null) &&
                        value.toString().startsWith(country.phonePrefix());

                return new ValidationResult<>(value, Objections.errorIf(!isValid, "Wrong country code prefix"));
            }

            @Override
            public List<Observable> dependencies() {
                return List.of(countryProperty);
            }
        };
    }

    public static Validator<FaxNumber> faxHasCountryPrefix(ObjectProperty<Country> countryProperty) {
        Objects.requireNonNull(countryProperty);

        return new Validator<>() {
            @Override
            public <VALIDATED extends FaxNumber>
            ValidationResult<VALIDATED> validate(VALIDATED value) {
                Country country = countryProperty.get();

                boolean isValid =
                        (country != null) &&
                        (value != null) &&
                        value.toString().startsWith(country.phonePrefix());

                return new ValidationResult<>(value, Objections.errorIf(!isValid, "Wrong country code prefix"));
            }

            @Override
            public List<Observable> dependencies() {
                return List.of(countryProperty);
            }
        };
    }
}