package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import pl.marcinchwedczuk.javafx.validation.lib.ConversionResult;
import pl.marcinchwedczuk.javafx.validation.lib.ValidatingValueConverter;

import java.util.List;
import java.util.Objects;

public class PhoneNumber {
    private final String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = Objects.requireNonNull(phoneNumber);
    }

    @Override
    public String toString() {
        return phoneNumber;
    }

    public static ValidatingValueConverter<String, PhoneNumber> converter() {
        return new ValidatingValueConverter<>() {
            @Override
            public ConversionResult<String, PhoneNumber> toModelValue(String uiValue) {
                if (uiValue == null || uiValue.isBlank()) {
                    return new ConversionResult<>(uiValue, null, List.of());
                } else {
                    return new ConversionResult<>(uiValue, new PhoneNumber(uiValue.trim()), List.of());
                }
            }

            @Override
            public String toUiValue(PhoneNumber modelValue) {
                if (modelValue == null) return "";
                return modelValue.toString();
            }
        };
    }
}
