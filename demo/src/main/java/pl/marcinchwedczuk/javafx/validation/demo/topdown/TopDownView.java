package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.marcinchwedczuk.javafx.validation.demo.UiService;
import pl.marcinchwedczuk.javafx.validation.controls.Banner;
import pl.marcinchwedczuk.javafx.validation.extras.UiBindings;
import pl.marcinchwedczuk.javafx.validation.extras.ValidationDecorator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TopDownView implements Initializable {
    public static TopDownView installAt(ObjectProperty<Node> contentProperty) {
        try {
            FXMLLoader loader = new FXMLLoader(TopDownView.class.getResource("TopDown.fxml"));

            Node subView = loader.load();
            contentProperty.setValue(subView);

            return (TopDownView) loader.getController();
        } catch (IOException e) {
            contentProperty.setValue(null);
            throw new RuntimeException(e);
        }
    }

    @FXML
    private ValidationDecorator countryE;
    @FXML
    private ComboBox<Country> countryF;

    @FXML
    private ValidationDecorator mobileE;
    @FXML
    private TextField mobileF;

    @FXML
    private ValidationDecorator faxE;
    @FXML
    private TextField faxF;

    @FXML
    private Label modelValues;

    private final TopDownViewModel viewModel = new TopDownViewModel(new UiService());

    @FXML
    private Banner invalidBanner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryF.setCellFactory(new CountryFlagCellFactory());
        countryF.setButtonCell(new CountryFlagCell());
        countryF.itemsProperty().bind(viewModel.countries);
        UiBindings.biBind(countryF.valueProperty(), viewModel.selectedCountry);
        countryE.displayErrorsFor(viewModel.selectedCountry);

        UiBindings.biBind(mobileF, viewModel.mobilePhone);
        mobileE.displayErrorsFor(viewModel.mobilePhone);

        UiBindings.biBind(faxF, viewModel.faxNumber);
        faxE.displayErrorsFor(viewModel.faxNumber);

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
