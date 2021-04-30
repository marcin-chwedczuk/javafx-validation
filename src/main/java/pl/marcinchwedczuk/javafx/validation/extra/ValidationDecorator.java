package pl.marcinchwedczuk.javafx.validation.extra;

import javafx.beans.DefaultProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import pl.marcinchwedczuk.javafx.validation.lib.ValidationError;

import java.util.List;

@DefaultProperty(value = "decorated")
public class ValidationDecorator extends VBox {
    private final VBox decoratedComponentsContainer;
    private final VBox validationMessagesContainer;

    private final SimpleListProperty<ValidationError> validationErrors =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    public ValidationDecorator() {
        decoratedComponentsContainer = new VBox();
        decoratedComponentsContainer.setPrefHeight(VBox.USE_COMPUTED_SIZE);
        decoratedComponentsContainer.setPrefWidth(VBox.USE_COMPUTED_SIZE);

        validationMessagesContainer = new VBox();
        validationMessagesContainer.setPrefHeight(VBox.USE_COMPUTED_SIZE);
        validationMessagesContainer.setPrefWidth(VBox.USE_COMPUTED_SIZE);
        validationMessagesContainer.setSpacing(2);

        this.setPrefHeight(USE_COMPUTED_SIZE);
        this.setPrefWidth(USE_COMPUTED_SIZE);
        this.setSpacing(2);

        super.getChildren().addAll(decoratedComponentsContainer, validationMessagesContainer);

        validationErrors.addListener((InvalidationListener) observable -> {
            List<ValidationError> errors = validationErrors.getValue();
            System.out.printf("Errors: %d%n", errors.size());

            var messagesFx = validationMessagesContainer.getChildren();

            if (messagesFx.size() > errors.size()) {
                messagesFx.remove(errors.size(), messagesFx.size());
            }
            while (messagesFx.size() < errors.size()) {
                messagesFx.add(new ValidationMessageFx());
            }

            for (int i = 0; i < errors.size(); i++) {
                ((ValidationMessageFx)messagesFx.get(i))
                        .setValidationError(errors.get(i));
            }
        });
    }

    public ObservableList<Node> getDecorated() {
        return decoratedComponentsContainer.getChildren();
    }

    public SimpleListProperty<ValidationError> validationErrorsProperty() {
        return validationErrors;
    }

    public class ValidationMessageFx extends HBox {
        private final Circle circle;
        private final Text text;

        public ValidationMessageFx() {
            super.setSpacing(5);
            super.setAlignment(Pos.BASELINE_LEFT);

            this.circle = new Circle(5.0, Color.RED);
            this.text = new Text();

            this.getChildren().addAll(circle, text);
        }

        public void setValidationError(ValidationError e) {
            this.text.setText("!!!" + e.message);
        }
    }
}
