module pl.marcinchwedczuk.javafx.validation {
    requires javafx.controls;
    requires javafx.fxml;

    exports pl.marcinchwedczuk.javafx.validation;
    exports pl.marcinchwedczuk.javafx.validation.demo.controls;
    exports pl.marcinchwedczuk.javafx.validation.extra;
    exports pl.marcinchwedczuk.javafx.validation.lib;

    // Allow @FXML injection to private fields.
    opens pl.marcinchwedczuk.javafx.validation.demo to javafx.fxml;
    opens pl.marcinchwedczuk.javafx.validation.demo.controls to javafx.fxml;
    opens pl.marcinchwedczuk.javafx.validation.demo.registration to javafx.fxml;
}