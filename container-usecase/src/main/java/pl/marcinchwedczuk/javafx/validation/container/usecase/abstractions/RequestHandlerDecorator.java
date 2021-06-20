package pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions;

import pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions.RequestHandler;

public abstract class RequestHandlerDecorator<
        REQUEST extends Request<RESPONSE>,
        RESPONSE
    > implements RequestHandler<REQUEST, RESPONSE> {

    private final RequestHandler<REQUEST, RESPONSE> original;

    protected RequestHandlerDecorator(RequestHandler<REQUEST, RESPONSE> original) {
        this.original = original;
    }

    @Override
    public final RESPONSE handle(REQUEST request) {
        return onHandleCalled(request);
    }

    protected abstract RESPONSE onHandleCalled(REQUEST request);

    protected RESPONSE callDecoratedComponent(REQUEST request) {
        return original.handle(request);
    }

    protected RequestHandler<REQUEST, RESPONSE> decoratedComponent() { return original; }
}
