package pl.marcinchwedczuk.javafx.validation.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
