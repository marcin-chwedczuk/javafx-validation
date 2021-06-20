package pl.marcinchwedczuk.javafx.validation.container;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Container implements ComponentResolver, AutoCloseable {
    public static Container createFrom(ContainerConfiguration config) {
        return null;
    }

    private List<Class<?>> components = new ArrayList<>();

    public Container(Class<?>... components) {
        this.components.addAll(Arrays.asList(components));
    }

    @Override
    public <T> T resolve(TypeTag<T> serviceType) {
        for (Class<?> component: components) {
            if (providesService(component, serviceType)) {
                return createComponent(component);
            }
        }

        throw new IllegalArgumentException("Unknown service: " + serviceType.getClass().toGenericString());
    }

    private <T> boolean providesService(Class<?> component, TypeTag<T> typeTag) {
        Class<?> requestedType = getRequestedType(typeTag);

        // Check component type itself, interfaces and iteratively base class + it's interfaces
        Class<?> current = component;
        while (current != null) {
            if (requestedType.equals(current)) {
                return true;
            }

            // TODO: check interface inheritance
            for (Class<?> anInterface : current.getInterfaces()) {
                if (requestedType.equals(anInterface)) {
                    return true;
                }
            }

            current = current.getSuperclass();
        }

        return false;
    }

    private <T> T createComponent(Class<?> component) {
        try {
            return (T)component.getDeclaredConstructors()[0].newInstance(new Object[] { });
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate: " + component, e);
        }
    }

    private static Class<?> getRequestedType(TypeTag<?> tag) {
        Type metaTypeTag = tag.getClass().getGenericSuperclass();
        Class<?> type = (Class<?>)((ParameterizedType)metaTypeTag).getActualTypeArguments()[0];
        return type;
    }

    @Override
    public void close() {
        throw new RuntimeException("not impl.");
    }
}
