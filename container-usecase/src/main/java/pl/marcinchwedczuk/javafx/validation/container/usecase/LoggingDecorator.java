package pl.marcinchwedczuk.javafx.validation.container.usecase;

import pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions.Request;
import pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions.RequestHandler;
import pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions.RequestHandlerDecorator;

public class LoggingDecorator<
        REQUEST extends Request<RESPONSE>,
        RESPONSE,
        C extends RESPONSE
    > extends RequestHandlerDecorator<REQUEST, RESPONSE> {

    public final C f = null;

    public LoggingDecorator(RequestHandler<REQUEST, RESPONSE> original) {
        super(original);
    }

    @Override
    protected RESPONSE onHandleCalled(REQUEST request) {
        System.out.println("Calling into " + decoratedComponent());
        RESPONSE resp = callDecoratedComponent(request);
        System.out.println("Result = " + resp);
        return resp;
    }
}
