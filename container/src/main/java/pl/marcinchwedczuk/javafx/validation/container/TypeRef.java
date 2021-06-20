package pl.marcinchwedczuk.javafx.validation.container;

import java.util.List;
import java.util.Objects;

class TypeRef {
    public static TypeRef genericType(Class<?> genericType, Class<?>... typeArguments) {
        TypeRef ref = new TypeRef(genericType, typeArguments);
        ref.validate();
        return ref;
    }

    public final Class<?> baseType;
    public final List<Class<?>> typeArguments;

    private TypeRef(Class<?> baseType, Class<?>... typeArguments) {
        this.baseType = Objects.requireNonNull(baseType);
        this.typeArguments = List.of(typeArguments);

        for (Class<?> typeArgument: typeArguments) {
            Objects.requireNonNull(typeArgument);
        }
    }

    private void validate() {
        checkValidNumberOfTypeArguments();

        // Checking for bounds can be skipped, if the bounds does not match
        // the component will not be present in the container anyway.
    }

    private void checkValidNumberOfTypeArguments() {
        int expected = baseType.getTypeParameters().length;
        int actual = typeArguments.size();
        if (expected != actual) {
            throw new IllegalArgumentException(String.format(
                    "Type %s expects exactly %d type parameters, but %d type parameters where passed.",
                    baseType.getName(), expected, actual));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeRef typeRef = (TypeRef) o;
        return baseType.equals(typeRef.baseType) &&
                typeArguments.equals(typeRef.typeArguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseType, typeArguments);
    }
}

