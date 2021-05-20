package pl.marcinchwedczuk.javafx.validation.demo.controls;

import javafx.scene.layout.VBox;

public class LayoutAwareVBox extends VBox {
    public LayoutAwareVBox() {
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        this.fireEvent(new LayoutEvent());
    }
}
