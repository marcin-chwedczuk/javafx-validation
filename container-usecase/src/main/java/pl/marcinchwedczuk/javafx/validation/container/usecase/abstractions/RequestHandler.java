package pl.marcinchwedczuk.javafx.validation.container.usecase.abstractions;

public interface RequestHandler<REQUEST extends Request<RESPONSE>, RESPONSE> {
    RESPONSE handle(REQUEST request);
}
