package pl.marcinchwedczuk.javafx.validation.demo.topdown;

public enum Country {
    POLAND {
        @Override
        String countryCode() {
            return "pl";
        }
    },

    RUSSIA {
        @Override
        String countryCode() {
            return "ru";
        }
    };

    abstract String countryCode();
}
