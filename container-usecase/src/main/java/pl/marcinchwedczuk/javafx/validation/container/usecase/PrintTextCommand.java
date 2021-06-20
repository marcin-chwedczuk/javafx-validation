package pl.marcinchwedczuk.javafx.validation.container.usecase;

import pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions.NoResponse;
import pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions.Request;

import java.util.Objects;

public class PrintTextCommand implements Request<NoResponse> {
    public final String text;

    public PrintTextCommand(String text) {
        this.text = Objects.requireNonNull(text);
    }
}
