package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

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

    public ComboBoxInspector selectValue(T item) {
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
}
