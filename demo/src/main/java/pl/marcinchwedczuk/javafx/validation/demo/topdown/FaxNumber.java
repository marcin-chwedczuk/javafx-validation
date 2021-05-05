package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import pl.marcinchwedczuk.javafx.validation.lib.ConversionResult;
import pl.marcinchwedczuk.javafx.validation.lib.ValidatingValueConverter;

import java.util.List;
import java.util.Objects;

public class FaxNumber {
    private final String faxNumber;

    public FaxNumber(String faxNumber) {
        this.faxNumber = Objects.requireNonNull(faxNumber);
    }

    @Override
    public String toString() {
        return faxNumber;
    }

    public static ValidatingValueConverter<String, FaxNumber> converter() {
        return new ValidatingValueConverter<>() {
            @Override
            public ConversionResult<String, FaxNumber> toModelValue(String uiValue) {
                if (uiValue == null || uiValue.isBlank()) {
                    return new ConversionResult<>(uiValue, null, List.of());
                } else {
                    return new ConversionResult<>(uiValue, new FaxNumber(uiValue.trim()), List.of());
                }
            }

            @Override
            public String toUiValue(FaxNumber modelValue) {
                if (modelValue == null) return "";
                return modelValue.toString();
            }
        };
    }
}
