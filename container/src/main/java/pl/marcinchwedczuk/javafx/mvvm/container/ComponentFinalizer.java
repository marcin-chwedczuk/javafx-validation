package pl.marcinchwedczuk.javafx.mvvm.container;

@FunctionalInterface
public interface ComponentFinalizer<C> {
    ComponentFinalizer<Object> EMPTY = c -> { };

    void finalize(C c);
}
