package pl.marcinchwedczuk.javafx.validation.demo.mainwindow;

import javafx.scene.control.Alert;

public class UiService {

    public void warningDialog(String message) {
        new Alert(Alert.AlertType.WARNING, message).showAndWait();
    }

    public void infoDialog(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
    }
}
