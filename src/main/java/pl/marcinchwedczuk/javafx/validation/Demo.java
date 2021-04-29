package pl.marcinchwedczuk.javafx.validation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Demo {

    public static Demo show() {
        try {
            FXMLLoader loader = new FXMLLoader(Demo.class.getResource("Demo.fxml"));

            Stage window = new Stage();
            window.setTitle("Validation Demo");
            window.setScene(new Scene(loader.load()));
            window.setMinWidth(640);
            window.setMinHeight(480);
            window.setResizable(true);

            Demo controller = (Demo)loader.getController();

            window.sizeToScene();
            window.show();

            return controller;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
