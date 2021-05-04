package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * inspired by: https://examples.javacodegeeks.com/desktop-java/javafx/combobox/javafx-combobox-example/
 */
public class CountryFlagCell extends ListCell<Country> {
    // Cache imageView for efficiency.
    private final ImageView imageView = new ImageView();

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
}