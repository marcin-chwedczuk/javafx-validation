package pl.marcinchwedczuk.javafx.validation.extras.usecase;

import pl.marcinchwedczuk.javafx.validation.extras.usecase.abstractions.EventHandler;

public class TextPrintedEventHandler2 implements EventHandler<TextPrintedEvent> {
    @Override
    public void handle(TextPrintedEvent event) {
        System.out.println("Text printed: " + event.text);
    }
}
