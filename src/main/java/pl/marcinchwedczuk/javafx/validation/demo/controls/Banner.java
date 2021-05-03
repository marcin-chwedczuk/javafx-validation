package pl.marcinchwedczuk.javafx.validation.demo.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class Banner extends HBox {
    private static PseudoClass CSS_TYPE_INFO = PseudoClass.getPseudoClass("type-info");
    private static PseudoClass CSS_TYPE_WARNING = PseudoClass.getPseudoClass("type-warning");
    private static PseudoClass CSS_TYPE_ERROR = PseudoClass.getPseudoClass("type-error");

    private final ObjectProperty<Type> type = new SimpleObjectProperty<>(this, "type", Type.INFO);

    public Banner() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "Banner.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        type.addListener(observable -> {
            updateStyle();
        });
        updateStyle();
    }

    private void updateStyle() {
        Type t = type.getValue();
        pseudoClassStateChanged(CSS_TYPE_INFO, (t == Type.INFO));
        pseudoClassStateChanged(CSS_TYPE_WARNING, (t == Type.WARN));
        pseudoClassStateChanged(CSS_TYPE_ERROR, (t == Type.ERROR));
    }

    public Type getType() {
        return type.get();
    }
    public ObjectProperty<Type> typeProperty() {
        return type;
    }
    public void setType(Type type) {
        this.type.set(type);
    }

    public enum Type {
        INFO, WARN, ERROR
    }
}
