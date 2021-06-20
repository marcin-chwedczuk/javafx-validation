package pl.marcinchwedczuk.javafx.validation.container.usecase;

import java.util.Objects;

public class TextPrintedEvent {
    public final String text;

    public TextPrintedEvent(String text) {
        this.text = Objects.requireNonNull(text);
    }
}
