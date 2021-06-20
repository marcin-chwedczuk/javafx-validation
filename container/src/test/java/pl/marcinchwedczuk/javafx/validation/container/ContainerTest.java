package pl.marcinchwedczuk.javafx.validation.container;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    @Test
    void can_resolve_non_generic_component() {
        Container container = new Container(NonGenericComponent.class);

        NonGenericComponent resolved = container.resolve(new TypeTag<NonGenericComponent>() {});
        assertThat(resolved)
                .isNotNull();
    }

    static class NonGenericComponent { }
}