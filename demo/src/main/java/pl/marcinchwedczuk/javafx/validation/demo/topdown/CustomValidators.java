package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import pl.marcinchwedczuk.javafx.validation.Objection;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.List;
import java.util.Objects;

public class CustomValidators {
    private CustomValidators() {
    }

    public static Validator<PhoneNumber> phoneHasCountryPrefix(ObjectProperty<Country> countryProperty) {
        Objects.requireNonNull(countryProperty);

        return new Validator<>() {
            @Override
            public <TT extends PhoneNumber>
            ValidationResult<TT> validate(TT value) {
                Country country = countryProperty.get();

                boolean isValid =
                        (country == null) ||
                                (value == null) ||
                                value.toString().startsWith(country.phonePrefix());

                if (isValid) {
                    return ValidationResult.success(value);
                }
                else {
                    return ValidationResult.failure(value, Objections.error("Wrong country code prefix."));
                }
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
            public <TT extends FaxNumber>
            ValidationResult<TT> validate(TT value) {
                Country country = countryProperty.get();

                boolean isValid =
                        (country == null) ||
                                (value == null) ||
                                value.toString().startsWith(country.phonePrefix());

                if (isValid) {
                    return ValidationResult.success(value);
                }
                else {
                    return ValidationResult.failure(value, Objections.error("Wrong country code prefix."));
                }
            }

            @Override
            public List<Observable> dependencies() {
                return List.of(countryProperty);
            }
        };
    }
}
