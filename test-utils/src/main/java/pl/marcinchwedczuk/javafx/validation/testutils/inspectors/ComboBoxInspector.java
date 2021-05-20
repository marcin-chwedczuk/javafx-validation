package pl.marcinchwedczuk.javafx.validation.testutils.inspectors;

import javafx.scene.control.ComboBox;
import org.testfx.api.FxRobot;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.fail;

public class ComboBoxInspector<T> extends BaseControlInspector {
    public ComboBoxInspector(FxRobot robot, String fxid) {
        super(robot, fxid);
    }

    private ComboBox<T> control() {
        return robot.lookup(idSelector()).queryComboBox();
    }

    public ComboBoxInspector<T> selectValue(T item) {
        ComboBox<T> control = control();

        AtomicBoolean selected = new AtomicBoolean(false);
        robot.interact(() -> {
            control.getSelectionModel().select(item);
            selected.set(control.getSelectionModel().getSelectedItem() == item);
        });

        if (!selected.get()) {
            fail("Cannot select '%s' value in ComboBox '%s'.", item, idSelector());
        }

        return this;
    }

    public ComboBoxInspector<T> assertNoValueSelected() {
        ComboBox<T> control = control();

        boolean isEmpty = control.getSelectionModel().isEmpty();
        T selectedValue = control.getSelectionModel().getSelectedItem();

        if (!isEmpty) {
            fail("ComboBox[%s] should be empty, but value '%s' is selected.",
                    fxid, selectedValue);
        }

        return this;
    }
}
