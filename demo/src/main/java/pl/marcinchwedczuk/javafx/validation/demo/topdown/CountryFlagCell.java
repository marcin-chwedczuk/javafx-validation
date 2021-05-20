package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.Size;
import javafx.css.SizeUnits;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * inspired by: https://examples.javacodegeeks.com/desktop-java/javafx/combobox/javafx-combobox-example/
 */
public class CountryFlagCell extends ListCell<Country> {
    // Cache imageView for efficiency.
    private final ImageView imageView = new ImageView();

    private final DoubleProperty imageHeight = new SimpleDoubleProperty(this, "imageHeight", emToPixels(1.2));

    public CountryFlagCell() {
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(new Size(1.2, SizeUnits.EM).pixels());
    }

    @Override
    public void updateItem(Country item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);
        setGraphic(null);

        if (!empty && (item != null)) {
            setText(item.name());

            Image flag = CountryFlags.loadFlag(item);
            if (flag != null) {
                imageView.setImage(flag);
                setGraphic(imageView);
            }
        }
    }

    public double getImageHeight() {
        return imageHeight.get();
    }

    public DoubleProperty imageHeightProperty() {
        return imageHeight;
    }

    public void setImageHeight(double imageHeight) {
        this.imageHeight.set(imageHeight);
    }

    private static double emToPixels(double em) {
        return new Size(1.2, SizeUnits.EM).pixels();
    }
}