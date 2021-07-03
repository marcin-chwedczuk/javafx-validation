package pl.marcinchwedczuk.javafx.mvvm.container;

public interface ComponentResolver {
    <T> T resolve(TypeTag<T> componentType);
}
