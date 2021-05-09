package pl.marcinchwedczuk.javafx.validation.demo.mainwindow;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class UiService {

    public void warningDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.getDialogPane().setId("warning-dialog");
        alert.showAndWait();
    }

    public void infoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.getDialogPane().setId("info-dialog");
        alert.showAndWait();
    }
}
