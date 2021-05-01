package pl.marcinchwedczuk.javafx.validation.extra;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class ControlPrototype extends Control {
    @Override
    protected Skin<?> createDefaultSkin() {
        return new ControlSkinPrototype(this);
    }
}
