package pl.marcinchwedczuk.javafx.validation.extra;

import javafx.beans.DefaultProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Skin;
import pl.marcinchwedczuk.javafx.validation.lib.Input;
import pl.marcinchwedczuk.javafx.validation.lib.Objection;

@DefaultProperty("content")
public class ValidationDecorator extends Control {

    private final ListProperty<Objection> objectionsProperty =
            new SimpleListProperty<>(this, "objections", FXCollections.observableArrayList());

    private final BooleanProperty pristine =
            new SimpleBooleanProperty(this, "pristine", true);

    private final ObjectProperty<Node> contentProperty =
            new SimpleObjectProperty<Node>(this, "content", null);

    public ValidationDecorator() {
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

    public final ListProperty<Objection> objectionsProperty() {
        return objectionsProperty;
    }
    public final ObservableList<Objection> getObjections() {
        return objectionsProperty.get();
    }
    public final void setObjections(ObservableList<Objection> objections) {
        objectionsProperty.set(objections);
    }

    public boolean isPristine() {
        return pristine.get();
    }
    public BooleanProperty pristineProperty() {
        return pristine;
    }
    public void setPristine(boolean pristine) {
        this.pristine.set(pristine);
    }

    public <UIV,MV> void displayErrorsFor(Input<UIV, MV> input) {
        this.objectionsProperty().bind(input.objectionsProperty());
        this.pristineProperty().bind(input.pristineProperty());
    }
}
