package pl.marcinchwedczuk.javafx.validation.container;

import javafx.css.PseudoClass;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ObjectionsList;
import pl.marcinchwedczuk.javafx.validation.testutils.utils.TakeScreenshotOnFailure;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
@TakeScreenshotOnFailure
public class ValidationDecoratorIT {
    private static final String DECORATED_COMPONENT_ID = "decoratedComponent";

    private TextField decoratedTextField;
    private ValidationDecorator validationDecorator;

    @Start
    private void start(Stage stage) {
        validationDecorator = new ValidationDecorator();
        decoratedTextField = new TextField();
        decoratedTextField.setId(DECORATED_COMPONENT_ID);

        validationDecorator.setContent(decoratedTextField);

        stage.setScene(new Scene(new VBox(validationDecorator), 200, 200));
        stage.show();
    }

    @Test
    void has_proper_css_classes_set(FxRobot robot) {
        assertThat(robot.lookup(".validation-decorator").queryAs(ValidationDecorator.class))
                .isSameAs(validationDecorator);

        // Check we have two different containers, one for the component and one for objections
        VBox componentContainer = robot.lookup(".validation-decorator .component-container").queryAs(VBox.class);
        VBox objectionsContainer = robot.lookup(".validation-decorator .objections-container").queryAs(VBox.class);

        assertThat(componentContainer)
                .isNotSameAs(objectionsContainer);
    }

    @Test
    void decorated_control_is_attached_to_component_container(FxRobot robot) {
        String cssSelector = String.format(
                ".validation-decorator .component-container #%s", DECORATED_COMPONENT_ID);

        assertThat(robot.lookup(cssSelector).queryTextInputControl())
                .isSameAs(decoratedTextField);
    }

    @Test
    void objections_are_attached_to_objections_container(FxRobot robot) {
        robot.interact(() -> {
            validationDecorator.objectionsProperty().set(
                    ObjectionsList.of(Objections.error("Message"))
            );
        });

        String cssSelector = ".validation-decorator .objections-container .objection";
        assertThat(robot.lookup(cssSelector))
                .isNotNull();
    }

    @Test
    void objections_have_severity_class_set(FxRobot robot) {
        robot.interact(() -> {
            validationDecorator.objectionsProperty().set(ObjectionsList.of(
                    Objections.error("Error"),
                    Objections.warning("Warning")
            ));
        });

        assertThat(robot.lookup(
                ".validation-decorator .objections-container .objection.severity-error .message"
        ).queryLabeled()).hasText("Error");

        assertThat(robot.lookup(
                ".validation-decorator .objections-container .objection.severity-warning .message"
        ).queryLabeled()).hasText("Warning");
    }


    @Test
    void with_errors_psuedoclass_is_properly_set(FxRobot robot) {
        assertComponentsContainerHasNoPseudoClass(robot);

        robot.interact(() -> {
            validationDecorator.objectionsProperty().set(
                    ObjectionsList.of(Objections.error("Error"))
            );
        });

        assertComponentsContainerPseudoClass(robot, ValidationDecoratorSkin.CSS_WITH_ERRORS);

        robot.interact(() -> {
            validationDecorator.objectionsProperty().set(ObjectionsList.EMPTY);
        });

        assertComponentsContainerHasNoPseudoClass(robot);
    }

    @Test
    void with_warnings_psuedoclass_is_properly_set(FxRobot robot) {
        assertComponentsContainerHasNoPseudoClass(robot);

        robot.interact(() -> {
            validationDecorator.objectionsProperty().set(
                    ObjectionsList.of(Objections.warning("Warning"))
            );
        });

        assertComponentsContainerPseudoClass(robot, ValidationDecoratorSkin.CSS_WITH_WARNINGS);

        robot.interact(() -> {
            validationDecorator.objectionsProperty().set(ObjectionsList.EMPTY);
        });

        assertComponentsContainerHasNoPseudoClass(robot);
    }

    @Test
    void if_has_errors_and_warnings_then_has_only_error_psuedoclass(FxRobot robot) {
        assertComponentsContainerHasNoPseudoClass(robot);

        robot.interact(() -> {
            validationDecorator.objectionsProperty().set(ObjectionsList.of(
                    Objections.error("Error"),
                    Objections.warning("Warning")
            ));
        });

        assertComponentsContainerPseudoClass(robot, ValidationDecoratorSkin.CSS_WITH_ERRORS);

        robot.interact(() -> {
            validationDecorator.objectionsProperty().set(ObjectionsList.of(
                    Objections.warning("Warning")
            ));
        });

        assertComponentsContainerPseudoClass(robot, ValidationDecoratorSkin.CSS_WITH_WARNINGS);
    }

    private void assertComponentsContainerPseudoClass(FxRobot robot, PseudoClass cssPseudoClass) {
        VBox container = robot.lookup(
                ".validation-decorator .component-container"
        ).queryAs(VBox.class);

        assertThat(container.getPseudoClassStates())
                .contains(cssPseudoClass);
    }

    private void assertComponentsContainerHasNoPseudoClass(FxRobot robot) {
        VBox container = robot.lookup(
                ".validation-decorator .component-container"
        ).queryAs(VBox.class);

        assertThat(container.getPseudoClassStates())
                .isEmpty();
    }
}
