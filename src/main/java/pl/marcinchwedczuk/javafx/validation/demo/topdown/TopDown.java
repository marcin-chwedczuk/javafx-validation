package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.marcinchwedczuk.javafx.validation.demo.UiService;
import pl.marcinchwedczuk.javafx.validation.controls.Banner;
import pl.marcinchwedczuk.javafx.validation.extra.UiBindings;
import pl.marcinchwedczuk.javafx.validation.extra.ValidationDecorator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TopDown implements Initializable {
    public static TopDown installAt(ObjectProperty<Node> contentProperty) {
        try {
            FXMLLoader loader = new FXMLLoader(TopDown.class.getResource("TopDown.fxml"));

            Node subView = loader.load();
            contentProperty.setValue(subView);

            return (TopDown) loader.getController();
        } catch (IOException e) {
            contentProperty.setValue(null);
            throw new RuntimeException(e);
        }
    }

    private final TopDownViewModel viewModel = new TopDownViewModel(new UiService()); // Bieda DI


    @FXML
    private Banner invalidBanner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        invalidBanner.visibleProperty()
                .bind(viewModel.showErrorBannerProperty());
        invalidBanner.managedProperty()
                .bind(viewModel.showErrorBannerProperty());
    }

    @FXML
    private void validate() {
        viewModel.validate();
    }
}
