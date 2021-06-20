package pl.marcinchwedczuk.javafx.validation.container;

import java.lang.reflect.Constructor;
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
        TypeRef requestedType = getRequestedType(serviceType);
        return resolveInternal(requestedType);
    }

    public <T> T resolveInternal(TypeRef requestedType) {
        for (Class<?> component: components) {
            if (providesService(component, requestedType)) {
                return createComponent(component);
            }
        }

        throw new IllegalArgumentException("Unknown service: " + requestedType.baseType +
                " " + requestedType.typeArguments + ".");
    }

    private <T> boolean providesService(Class<?> component, TypeRef requestedType) {
        // Check component type itself, interfaces and iteratively base class + it's interfaces
        Class<?> current = component;
        while (current != null) {
            if (requestedType.baseType.equals(current)) {
                return true;
            }

            // TODO: check interface inheritance
            for (Class<?> anInterface : current.getInterfaces()) {
                if (requestedType.baseType.equals(anInterface)) {
                    return true;
                }
            }

            current = current.getSuperclass();
        }

        return false;
    }

    private <T> T createComponent(Class<?> component) {
        try {
            // If has parameter-less ctor then use it
            for (Constructor<?> ctor : component.getDeclaredConstructors()) {
                if (ctor.getParameterCount() == 0) {
                    return (T)ctor.newInstance(new Object[] { });
                }
            }

            // Assuming that we have only one constructor, we will use it
            if (component.getDeclaredConstructors().length == 1) {
                Constructor<?> ctor = component.getDeclaredConstructors()[0];
                List<Object> arguments = new ArrayList<Object>(ctor.getParameterCount());
                for (int i = 0; i < ctor.getParameterCount(); i++) {
                    Type paramType = ctor.getGenericParameterTypes()[i];
                    TypeRef typeRef = TypeRef.fromReflectionType(paramType);
                    arguments.add(resolveInternal(typeRef));
                }

                return (T)ctor.newInstance(arguments.toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate: " + component, e);
        }

        throw new RuntimeException("No suitable ctor found for class: " + component);
    }

    private static TypeRef getRequestedType(TypeTag<?> typeTag) {
        Type metaTypeTag = typeTag.getClass().getGenericSuperclass();
        Type requestedType = ((ParameterizedType)metaTypeTag).getActualTypeArguments()[0];

        if (requestedType instanceof Class<?>) {
            // Non generic type
            return TypeRef.genericType((Class<?>)requestedType);
        }

        if (requestedType instanceof ParameterizedType) {
            ParameterizedType parameterizedRequestedType = (ParameterizedType)requestedType;

            List<Class<?>> castedTypeArgs = new ArrayList<>();
            for (Type typeArg : parameterizedRequestedType.getActualTypeArguments()) {
                if (!(typeArg instanceof Class<?>)) {
                    throw new RuntimeException("Unsupported type constraint: " + typeArg);
                }
                castedTypeArgs.add((Class<?>)typeArg);
            }

            return TypeRef.genericType(
                    (Class<?>)parameterizedRequestedType.getRawType(),
                    castedTypeArgs.toArray(new Class<?>[] { }));
        }

        // TODO: Arrays and other crap
        throw new RuntimeException("Type " + requestedType + " is not supported.");
    }

    @Override
    public void close() {
        throw new RuntimeException("not impl.");
    }
}
