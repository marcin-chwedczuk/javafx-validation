package pl.marcinchwedczuk.javafx.validation.container.usecase;

import pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions.EventHandler;

public class TextPrintedEventHandler implements EventHandler<TextPrintedEvent> {
    @Override
    public void handle(TextPrintedEvent event) {
        System.out.println("Text printed: " + event.text);
    }
}
