package pl.marcinchwedczuk.javafx.validation.demo.range;

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

public class NumberRange implements Initializable {
    public static NumberRange installAt(ObjectProperty<Node> contentProperty) {
        try {
            FXMLLoader loader = new FXMLLoader(NumberRange.class.getResource("NumberRange.fxml"));

            Node subView = loader.load();
            contentProperty.setValue(subView);

            return (NumberRange) loader.getController();
        } catch (IOException e) {
            contentProperty.setValue(null);
            throw new RuntimeException(e);
        }
    }

    private final NumberRangeViewModel viewModel = new NumberRangeViewModel(new UiService()); // Bieda DI

    @FXML
    private TextField fromF;
    @FXML
    private ValidationDecorator fromE;

    @FXML
    private TextField toF;
    @FXML
    private ValidationDecorator toE;

    @FXML
    private Label modelRange;

    @FXML
    private Banner invalidBanner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UiBindings.biBind(fromF, viewModel.from);
        fromE.displayErrorsFor(viewModel.from);

        UiBindings.biBind(toF, viewModel.to);
        toE.displayErrorsFor(viewModel.to);

        modelRange.textProperty().bind(
                UiBindings.map2(viewModel.from.modelValueProperty(), viewModel.to.modelValueProperty(), (from, to) ->
                        String.format("range(%d, %d)", from, to))
        );

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
