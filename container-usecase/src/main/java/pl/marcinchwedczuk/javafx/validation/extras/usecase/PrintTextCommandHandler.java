package pl.marcinchwedczuk.javafx.validation.extras.usecase;

import pl.marcinchwedczuk.javafx.validation.extras.usecase.abstractions.NoResponse;
import pl.marcinchwedczuk.javafx.validation.extras.usecase.abstractions.RequestHandler;

public class PrintTextCommandHandler implements RequestHandler<PrintTextCommand, NoResponse> {
    @Override
    public NoResponse handle(PrintTextCommand printTextCommand) {
        System.out.println(printTextCommand.text);
        return NoResponse.INSTANCE;
    }
}
