package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import pl.marcinchwedczuk.javafx.validation.ConversionResult;
import pl.marcinchwedczuk.javafx.validation.ValueConverter;

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

    public static ValueConverter<String, PhoneNumber> converter() {
        return new ValueConverter<>() {
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
