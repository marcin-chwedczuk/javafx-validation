package pl.marcinchwedczuk.javafx.mvvm.container;

import java.util.ArrayList;
import java.util.List;

public class ContainerConfiguration {
    private final List<RegistrationBuilder<?>> components = new ArrayList<>();

    public <COMPONENT>
    RegistrationBuilder<COMPONENT> registerComponent(Class<COMPONENT> componentClass) {
        RegistrationBuilder<COMPONENT> builder = new RegistrationBuilder<>(componentClass);
        components.add(builder);
        return builder;
    }

    public static ContainerConfiguration merge(ContainerConfiguration... configurations) {
        throw new RuntimeException("not impl.");
    }
}
