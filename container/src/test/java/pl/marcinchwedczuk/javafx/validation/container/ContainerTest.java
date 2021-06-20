package pl.marcinchwedczuk.javafx.validation.container;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ContainerTest {
    @Test
    void can_resolve_non_generic_component() {
        Container container = new Container(NonGenericComponent.class);

        NonGenericComponent resolved = container.resolve(new TypeTag<NonGenericComponent>() {});
        assertThat(resolved)
                .isNotNull();
    }

    @Test
    void can_resolve_generic_component() {
        Container container = new Container(ArrayList.class);

        List<String> resolved = container.resolve(new TypeTag<ArrayList<String>>() {});
        assertThat(resolved)
                .isNotNull();
    }

    static class NonGenericComponent { }

    static class SimpleDependencyA { }
    static class SimpleDependencyB { }
    static class ComponentWithDependencies {
        private final SimpleDependencyA depA;
        private final SimpleDependencyB depB;

        ComponentWithDependencies(SimpleDependencyA depA, SimpleDependencyB depB) {
            this.depA = depA;
            this.depB = depB;
        }
    }

    interface Foo<T> { }
    static class FooImpl<T> implements Foo<T> { }
    static class FooDecorator<T> implements Foo<T> { }
}