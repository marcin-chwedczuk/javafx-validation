package pl.marcinchwedczuk.javafx.validation.container.usecase;

import pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions.NoResponse;
import pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions.RequestHandler;

public class PrintTextCommandHandler implements RequestHandler<PrintTextCommand, NoResponse> {
    @Override
    public NoResponse handle(PrintTextCommand printTextCommand) {
        System.out.println(printTextCommand.text);
        return NoResponse.INSTANCE;
    }
}
