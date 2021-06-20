package pl.marcinchwedczuk.javafx.validation.container;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RegistrationBuilder<COMPONENT> {
    private final Class<COMPONENT> componentClass;

    private ComponentName componentName = null;
    private final Set<Class<?>> implementingSet = null;
    private ComponentInitializer<? super COMPONENT> initializer = ComponentInitializer.EMPTY;
    private ComponentFinalizer<? super COMPONENT> finalizer = ComponentFinalizer.EMPTY;
    private Scope scope = Scope.ALWAYS_NEW;

    public RegistrationBuilder(Class<COMPONENT> componentClass) {
        this.componentClass = Objects.requireNonNull(componentClass);
    }

    Component build() {
        return new Component(componentClass);
    }

    public RegistrationBuilder<COMPONENT> named(String name) {
        return this;
    }

    public RegistrationBuilder<COMPONENT> implementing(Class<? super COMPONENT> interface_) {
        return this;
    }

    public RegistrationBuilder<COMPONENT> initializeWith(ComponentInitializer<? super COMPONENT> initializer) {
        return this;
    }

    public RegistrationBuilder<COMPONENT> finalizeWith(ComponentInitializer<? super COMPONENT> finalizer) {
        return this;
    }

    public RegistrationBuilder<COMPONENT> scope(Scope scope) {
        return this;
    }

    public RegistrationBuilder<COMPONENT> asDecoratorFor(Class<?> decorated) {
        return this;
    }
}
