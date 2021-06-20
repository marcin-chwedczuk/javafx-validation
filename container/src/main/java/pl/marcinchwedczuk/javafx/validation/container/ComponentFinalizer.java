package pl.marcinchwedczuk.javafx.validation.container;

@FunctionalInterface
public interface ComponentFinalizer<C> {
    ComponentFinalizer<Object> EMPTY = c -> { };

    void finalize(C c);
}
