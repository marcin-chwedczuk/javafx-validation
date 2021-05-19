package pl.marcinchwedczuk.javafx.validation.demo.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;

import java.util.EventListener;

public class LayoutAwareVBox extends VBox {
    public LayoutAwareVBox() { }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        this.fireEvent(new LayoutEvent());
    }
}
