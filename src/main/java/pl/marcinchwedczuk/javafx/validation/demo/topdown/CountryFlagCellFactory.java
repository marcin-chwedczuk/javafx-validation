package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CountryFlagCellFactory implements Callback<ListView<Country>, ListCell<Country>> {
    @Override
    public ListCell<Country> call(ListView<Country> listView) {
        return new CountryFlagCell();
    }
}
