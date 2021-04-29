module pl.marcinchwedczuk.javafx.validation {
    requires javafx.controls;
    requires javafx.fxml;

    exports pl.marcinchwedczuk.javafx.validation;
    // exports pl.marcinchwedczuk.javafx.validation.demo;

    // Allow @FXML injection to private fields.
    opens pl.marcinchwedczuk.javafx.validation.demo to javafx.fxml;
}