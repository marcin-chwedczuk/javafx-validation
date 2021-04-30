package pl.marcinchwedczuk.javafx.validation.extra;

import javafx.beans.DefaultProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import pl.marcinchwedczuk.javafx.validation.lib.Objection;
import pl.marcinchwedczuk.javafx.validation.lib.ObjectionSeverity;
import pl.marcinchwedczuk.javafx.validation.lib.Input;

import java.util.List;

@DefaultProperty(value = "decorated")
public class ValidationDecorator extends VBox {
    private final VBox decoratedComponentsContainer;
    private final VBox validationMessagesContainer;

    private final SimpleListProperty<Objection> objectionsProperty =
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

        objectionsProperty.addListener((InvalidationListener) observable -> {
            List<Objection> objections = this.objectionsProperty.getValue();

            var messagesFx = validationMessagesContainer.getChildren();

            if (messagesFx.size() > objections.size()) {
                messagesFx.remove(objections.size(), messagesFx.size());
            }
            while (messagesFx.size() < objections.size()) {
                messagesFx.add(new ValidationMessageFx());
            }

            for (int i = 0; i < objections.size(); i++) {
                ((ValidationMessageFx)messagesFx.get(i)).setObjection(objections.get(i));
            }
        });
    }

    public ObservableList<Node> getDecorated() {
        return decoratedComponentsContainer.getChildren();
    }

    public SimpleListProperty<Objection> objectionsProperty() {
        return objectionsProperty;
    }

    public <UIV,MV> void displayErrorsFor(Input<UIV, MV> input) {
        this.objectionsProperty().bind(input.objectionsProperty());
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

        public void setObjection(Objection e) {
            this.text.setText("!!!" + e.message);
            this.circle.setFill(e.severity == ObjectionSeverity.ERROR ? Color.RED : Color.ORANGE);
        }
    }
}
