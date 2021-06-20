package pl.marcinchwedczuk.javafx.validation.container;

@FunctionalInterface
public interface ComponentInitializer<C> {
   ComponentInitializer<Object> EMPTY = c -> { };

    void initialize(C c);
}
