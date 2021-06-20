package pl.marcinchwedczuk.javafx.validation.container;

import java.util.Objects;

public class ComponentName {
    public static ComponentName fromClass(Class<?> clazz) {
        return new ComponentName(clazz.getName());
    }

    private final String name;

    public ComponentName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String name() { return name; }

    @Override
    public String toString() {
        return String.format("ComponentName(%s)", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentName that = (ComponentName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
