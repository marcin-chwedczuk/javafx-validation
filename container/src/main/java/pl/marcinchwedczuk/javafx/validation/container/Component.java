package pl.marcinchwedczuk.javafx.validation.container;

public class Component {
    private final Class<?> componentClass;

    public Component(Class<?> componentClass) {
        this.componentClass = componentClass;
    }

    // TODO: Create Tag<List<Foo>> class for generic types
    // new TypeTag<List<String>>() -> to ma jedno pole może uda się wyłuskać z tego typ.
    // tag.getGenericType() tag.getGenericParameters();
    public boolean matchesGenericRequest(Class<?> genericType, Class<?>... genericArguments) {
        // for t: interfaces:
        //  if genericArguments fullfill-bounds of t:
        //      return new t();
        // error "no such component"
        //
        // T - cannot be scoped, just to avoid the hassle of keeping T<Foo> and T<Bar> in scopes
        // searched types can be overriden by implementing clause
        throw new RuntimeException("not impl.");
    }
}
