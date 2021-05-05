package pl.marcinchwedczuk.javafx.validation.demo.topdown;

public enum Country {
    POLAND {
        @Override
        String countryCode() {
            return "pl";
        }

        @Override
        String phonePrefix() {
            return "+48";
        }
    },

    RUSSIA {
        @Override
        String countryCode() {
            return "ru";
        }

        @Override
        String phonePrefix() {
            return "+7";
        }
    };

    abstract String countryCode();
    abstract String phonePrefix();
}
