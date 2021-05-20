package pl.marcinchwedczuk.javafx.validation.demo.mainwindow;

import javafx.scene.control.Alert;

public class UiService {

    public void warningDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setTitle("Warning");
        alert.getDialogPane().setId("warning-dialog");
        alert.showAndWait();
    }

    public void infoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setTitle("Information");
        // ID for tests
        alert.getDialogPane().setId("info-dialog");
        alert.showAndWait();
    }
}
