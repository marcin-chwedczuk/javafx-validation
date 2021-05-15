package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import pl.marcinchwedczuk.javafx.validation.Objection;
import pl.marcinchwedczuk.javafx.validation.ObjectionSeverity;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.extras.ValidationDecorator;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static pl.marcinchwedczuk.javafx.validation.ObjectionSeverity.ERROR;

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
        List<Objection> errors = Objections.onlyOfSeverity(getObjections(), ERROR);

        Assertions.assertThat(errors)
                .as(makeDescription(ERROR))
                .extracting(x -> x.userMessage)
                .contains(error);

        return this;
    }

    public ValidationDecoratorInspector assertDoesNotShowError(String error) {
        List<Objection> errors = Objections.onlyOfSeverity(getObjections(), ERROR);

        Assertions.assertThat(errors)
                .as(makeDescription(ERROR))
                .extracting(x -> x.userMessage)
                .doesNotContain(error);

        return this;
    }

    public ValidationDecoratorInspector assertNoErrorVisible() {
        List<Objection> errors = Objections.onlyOfSeverity(getObjections(), ERROR);

        Assertions.assertThat(errors)
                .as(makeDescription(ERROR))
                .extracting(x -> x.userMessage)
                .isEmpty();

        return this;
    }

    private String makeDescription(ObjectionSeverity severity) {
        return "ValidationDecorator(id=" + fxid + ") " + (
                severity == ERROR ? "validation errors" :
                severity == ObjectionSeverity.WARNING ? "validation warnings" :
                "objections"
                );
    }
}
