package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import javafx.scene.control.ListCell;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.util.WaitForAsyncUtils;
import pl.marcinchwedczuk.javafx.validation.Objection;
import pl.marcinchwedczuk.javafx.validation.ObjectionSeverity;
import pl.marcinchwedczuk.javafx.validation.extras.ValidationDecorator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ValidationDecoratorInspector extends BaseControlInspector {
    public ValidationDecoratorInspector(FxRobot robot, String fxid) {
        super(robot, fxid);
    }

    private List<Objection> getObjections() {
        ValidationDecorator control = robot
                .lookup(idSelector())
                .queryAs(ValidationDecorator.class);

        // TODO: This is very fishy - the control lives on
        // JavaFX thread, so here we have access from
        // multiple threads.
        // Reported as: https://github.com/TestFX/TestFX/issues/727
        return control.getObjections();
    }

    public ValidationDecoratorInspector assertShowsError(String error) {
        List<Objection> errors = getObjections().stream()
                .filter(o -> o.severity == ObjectionSeverity.ERROR)
                .collect(toList());

        Assertions.assertThat(errors)
                .as("ValidationDecorator[" + fxid + "] errors")
                .extracting(x -> x.userMessage)
                .contains(error);

        return this;
    }

    public ValidationDecoratorInspector assertNoErrorVisible() {
        List<Objection> errors = getObjections().stream()
                .filter(o -> o.severity == ObjectionSeverity.ERROR)
                .collect(toList());

        Assertions.assertThat(errors)
                .as("ValidationDecorator[" + fxid + "] errors")
                .extracting(x -> x.userMessage)
                .isEmpty();

        return this;
    }
}
