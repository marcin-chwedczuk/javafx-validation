module pl.marcinchwedczuk.javafx.validation {
    requires javafx.controls;
    requires javafx.fxml;

    exports pl.marcinchwedczuk.javafx.validation;

    // Allow @FXML injection to private fields.
    opens pl.marcinchwedczuk.javafx.validation to javafx.fxml;
}