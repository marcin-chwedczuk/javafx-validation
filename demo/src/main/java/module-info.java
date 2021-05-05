module pl.marcinchwedczuk.javafx.validation.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires pl.marcinchwedczuk.javafx.validation.extras;
    requires pl.marcinchwedczuk.javafx.validation;

    exports pl.marcinchwedczuk.javafx.validation;

    // Allow @FXML injection to private fields.
    opens pl.marcinchwedczuk.javafx.validation.controls to javafx.fxml;
    opens pl.marcinchwedczuk.javafx.validation.demo to javafx.fxml;
    opens pl.marcinchwedczuk.javafx.validation.demo.registration to javafx.fxml;
    opens pl.marcinchwedczuk.javafx.validation.demo.range to javafx.fxml;
    opens pl.marcinchwedczuk.javafx.validation.demo.topdown to javafx.fxml;

}