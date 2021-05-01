package pl.marcinchwedczuk.javafx.validation.extra;

import javafx.beans.DefaultProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import pl.marcinchwedczuk.javafx.validation.lib.Input;
import pl.marcinchwedczuk.javafx.validation.lib.Objection;

@DefaultProperty("content")
public class ValidationDecorator2 extends Control {

    private final SimpleListProperty<Objection> objectionsProperty =
            new SimpleListProperty<>(this, "objections", FXCollections.observableArrayList());

    private final ObjectProperty<Node> contentProperty =
            new SimpleObjectProperty<Node>(this, "content", null);

    public ValidationDecorator2() {
        this.setPrefHeight(USE_COMPUTED_SIZE);
        this.setPrefWidth(USE_COMPUTED_SIZE);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ValidationDecoratorSkin(this);
    }

    public final ObjectProperty<Node> contentProperty() {
        return contentProperty;
    }
    public final void setContent(Node value) {
        contentProperty().set(value);
    }
    public final Node getContent() {
        return contentProperty().get();
    }

    public SimpleListProperty<Objection> objectionsProperty() {
        return objectionsProperty;
    }
    public ObservableList<Objection> getObjections() {
        return objectionsProperty.get();
    }
    public void setObjections(ObservableList<Objection> objections) {
        objectionsProperty.set(objections);
    }

    public <UIV,MV> void displayErrorsFor(Input<UIV, MV> input) {
        this.objectionsProperty().bind(input.objectionsProperty());
    }
}
