package pl.marcinchwedczuk.javafx.mvvm.container;

import org.junit.jupiter.api.Test;

import java.util.List;

public class JavaReflectionTests {

    @Test
    void can_get_generic_parameters() {
        Class<?> c = GenericType.class;

        c.getTypeParameters();
    }

    @Test
    void playing_with_type_tag() {
        new GenericType<>().toString();

        // TypeTag<?> typeTag = new TypeTag<GenericType<ArrayList<String>, String, Object>>() { };
        // Type superClazz = typeTag.getClass().getGenericSuperclass();
    }

    public static class GenericType<A extends List<B>, B extends C, C extends A> { }
}
