package pl.marcinchwedczuk.javafx.mvvm.container;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ContainerTest {
    @Test
    void can_resolve_non_generic_component() {
        Container container = new Container(NonGenericComponent.class);

        NonGenericComponent resolved = container.resolve(new TypeTag<NonGenericComponent>() {});
        assertThat(resolved)
                .isNotNull();
    }

    @Test
    void can_resolve_component_with_dependencies() {
        Container container = new Container(
                SimpleDependencyA.class,
                SimpleDependencyB.class,
                ComponentWithDependencies.class);

        ComponentWithDependencies resolved = container.resolve(new TypeTag<ComponentWithDependencies>() {});
        assertThat(resolved)
                .isNotNull();

        assertThat(resolved.depA)
                .isNotNull();

        assertThat(resolved.depB)
                .isNotNull();
    }

    @Test
    void can_resolve_generic_component() {
        Container container = new Container(ArrayList.class);

        List<String> resolved = container.resolve(new TypeTag<ArrayList<String>>() {});
        assertThat(resolved)
                .isNotNull();
    }

    @Test
    void can_resolve_component_with_generic_dependencies() {
        Container container = new Container(
                ArrayList.class,
                ComponentWithGenericDependencies.class);

        ComponentWithGenericDependencies resolved = container.resolve(new TypeTag<ComponentWithGenericDependencies>() {});
        assertThat(resolved)
                .isNotNull();

        assertThat(resolved.ints)
                .isNotNull();

        assertThat(resolved.strings)
                .isNotNull();

        assertThat(resolved.ints)
                .isNotSameAs(resolved.strings);
    }

    @Test
    @Disabled("not working")
    void mediator_sample() {
        Container container = new Container(
                PrintTextCommandHandler.class,
                DoFooCommandHandler.class,
                DoBarCommandHandler.class
        );

        // PrintTextCommand
        PrintTextCommand cmd = new PrintTextCommand();
        RequestHandler<PrintTextCommand, PrintTextCommandResponse> handler = container.resolve(cmd.handlerTypeTag);
        assertThat(handler)
                .isNotNull();
        PrintTextCommandResponse response = handler.handle(cmd);
        assertThat(response)
                .isNotNull();

        // DoFooCommand
        DoFooCommand fooCmd = new DoFooCommand();
        RequestHandler<DoFooCommand, DoFooCommandResponse> fooHandler = container.resolve(fooCmd.handlerTypeTag);
        assertThat(fooHandler)
                .isNotNull();
        DoFooCommandResponse fooResponse = fooHandler.handle(fooCmd);
        assertThat(fooResponse)
                .isNotNull();

        // DoBarCommand
        DoBarCommand barCmd = new DoBarCommand();
        RequestHandler<DoBarCommand, DoBarCommandResponse> barHandler = container.resolve(barCmd.handlerTypeTag);
        assertThat(fooHandler)
                .isNotNull();
        DoBarCommandResponse barResponse = barHandler.handle(barCmd);
        assertThat(barResponse)
                .isNotNull();
    }

    @Test
    void mediator_sample_with_decorator() {

    }

    static class NonGenericComponent { }

    static class SimpleDependencyA { }
    static class SimpleDependencyB { }
    static class ComponentWithDependencies {
        public final SimpleDependencyA depA;
        public final SimpleDependencyB depB;

        ComponentWithDependencies(SimpleDependencyA depA, SimpleDependencyB depB) {
            this.depA = depA;
            this.depB = depB;
        }
    }

    static class ComponentWithGenericDependencies {
        public final List<String> strings;
        public final List<Integer> ints;

        ComponentWithGenericDependencies(List<String> strings, List<Integer> ints) {
            this.strings = strings;
            this.ints = ints;
        }
    }

    interface Foo<T> { }
    static class FooImpl<T> implements Foo<T> { }
    static class FooDecorator<T> implements Foo<T> { }

    static abstract class Request<REQ extends Request<REQ, RESP>, RESP> {
        public final TypeTag<RequestHandler<REQ, RESP>> handlerTypeTag;

        protected Request(TypeTag<RequestHandler<REQ, RESP>> handlerTypeTag) {
            this.handlerTypeTag = Objects.requireNonNull(handlerTypeTag);
        }
    }

    static class PrintTextCommandResponse { }
    static class PrintTextCommand extends Request<PrintTextCommand, PrintTextCommandResponse> {
        public PrintTextCommand() { super(new TypeTag<>() { }); }
    }

    static class DoFooCommandResponse { }
    static class DoFooCommand extends Request<DoFooCommand, DoFooCommandResponse> {
        protected DoFooCommand() { super(new TypeTag<>() { }); }
    }

    static class DoBarCommandResponse { }
    static class DoBarCommand extends Request<DoBarCommand, DoBarCommandResponse> {
        protected DoBarCommand() { super(new TypeTag<>() { }); }
    }

    interface RequestHandler<REQ extends Request<REQ, RESP>, RESP> {
        RESP handle(REQ request);
    }

    static class PrintTextCommandHandler implements RequestHandler<PrintTextCommand, PrintTextCommandResponse> {
        @Override
        public PrintTextCommandResponse handle(PrintTextCommand request) {
            return new PrintTextCommandResponse();
        }
    }

    static class DoFooCommandHandler implements RequestHandler<DoFooCommand, DoFooCommandResponse> {
        @Override
        public DoFooCommandResponse handle(DoFooCommand request) {
            return new DoFooCommandResponse();
        }
    }

    static class DoBarCommandHandler implements RequestHandler<DoBarCommand, DoBarCommandResponse> {
        @Override
        public DoBarCommandResponse handle(DoBarCommand request) {
            return new DoBarCommandResponse();
        }
    }
}