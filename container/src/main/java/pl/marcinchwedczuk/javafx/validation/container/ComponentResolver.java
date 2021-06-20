package pl.marcinchwedczuk.javafx.validation.container;

public interface ComponentResolver {
    <T> T resolve(TypeTag<T> componentType);
}
