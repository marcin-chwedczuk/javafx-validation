package pl.marcinchwedczuk.javafx.validation.container;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
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
    void can_resolve_component_with_dependencies() {
        Container container = new Container(
                SimpleDependencyA.class,
                SimpleDependencyB.class,
                ComponentWithDependencies.class);

        ComponentWithDependencies resolved = container.resolve(new TypeTag<ComponentWithDependencies>() {});
        assertThat(resolved)
                .isNotNull();

        assertThat(resolved.depA)
                .isNotNull();

        assertThat(resolved.depB)
                .isNotNull();
    }

    @Test
    void can_resolve_generic_component() {
        Container container = new Container(ArrayList.class);

        List<String> resolved = container.resolve(new TypeTag<ArrayList<String>>() {});
        assertThat(resolved)
                .isNotNull();
    }

    @Test
    void can_resolve_component_with_generic_dependencies() {
        Container container = new Container(
                ArrayList.class,
                ComponentWithGenericDependencies.class);

        ComponentWithGenericDependencies resolved = container.resolve(new TypeTag<ComponentWithGenericDependencies>() {});
        assertThat(resolved)
                .isNotNull();

        assertThat(resolved.ints)
                .isNotNull();

        assertThat(resolved.strings)
                .isNotNull();

        assertThat(resolved.ints)
                .isNotSameAs(resolved.strings);
    }

    static class NonGenericComponent { }

    static class SimpleDependencyA { }
    static class SimpleDependencyB { }
    static class ComponentWithDependencies {
        public final SimpleDependencyA depA;
        public final SimpleDependencyB depB;

        ComponentWithDependencies(SimpleDependencyA depA, SimpleDependencyB depB) {
            this.depA = depA;
            this.depB = depB;
        }
    }

    static class ComponentWithGenericDependencies {
        public final List<String> strings;
        public final List<Integer> ints;

        ComponentWithGenericDependencies(List<String> strings, List<Integer> ints) {
            this.strings = strings;
            this.ints = ints;
        }
    }

    interface Foo<T> { }
    static class FooImpl<T> implements Foo<T> { }
    static class FooDecorator<T> implements Foo<T> { }
}