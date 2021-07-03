package pl.marcinchwedczuk.javafx.mvvm.container;

@FunctionalInterface
public interface ComponentInitializer<C> {
   ComponentInitializer<Object> EMPTY = c -> { };

    void initialize(C c);
}
