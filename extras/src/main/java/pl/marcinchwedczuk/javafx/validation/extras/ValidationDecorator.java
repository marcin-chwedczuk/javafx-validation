package pl.marcinchwedczuk.javafx.validation.extras;

import javafx.beans.DefaultProperty;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;
import pl.marcinchwedczuk.javafx.validation.Input;
import pl.marcinchwedczuk.javafx.validation.ObjectionsList;

@DefaultProperty("content")
public class ValidationDecorator extends Control {

    private final ObjectProperty<ObjectionsList> objections =
            new SimpleObjectProperty<>(this, "objections", ObjectionsList.EMPTY);

    private final BooleanProperty pristine =
            new SimpleBooleanProperty(this, "pristine", true);

    private final ObjectProperty<Node> contentProperty =
            new SimpleObjectProperty<Node>(this, "content", null);

    public ValidationDecorator() {
        this.getStyleClass().setAll("validation-decorator");

        this.setPrefHeight(Region.USE_COMPUTED_SIZE);
        this.setPrefWidth(Region.USE_COMPUTED_SIZE);
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

    public ObjectionsList getObjections() {
        return objections.get();
    }

    public ObjectProperty<ObjectionsList> objectionsProperty() {
        return objections;
    }

    public void setObjections(ObjectionsList objectionsProperty) {
        this.objections.set(objectionsProperty);
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

    public <UIV, MV> void displayErrorsFor(Input<UIV, MV> input) {
        this.objections.bind(input.objectionsProperty());
        this.pristineProperty().bind(input.pristineProperty());
    }
}
