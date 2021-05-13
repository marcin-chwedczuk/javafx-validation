package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import javafx.scene.control.ListCell;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import pl.marcinchwedczuk.javafx.validation.Objection;
import pl.marcinchwedczuk.javafx.validation.ObjectionSeverity;
import pl.marcinchwedczuk.javafx.validation.extras.ValidationDecorator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ValidationDecoratorInspector {
    private final FxRobot robot;
    private final String fxid;

    public ValidationDecoratorInspector(FxRobot robot, String fxid) {
        this.robot = Objects.requireNonNull(robot);
        this.fxid = Objects.requireNonNull(fxid);
    }

    private List<Objection> getObjections() {
        ValidationDecorator control = robot
                .lookup(fxid)
                .queryAs(ValidationDecorator.class);

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
