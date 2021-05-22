package pl.marcinchwedczuk.javafx.validation.impl;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.Test;
import pl.marcinchwedczuk.javafx.validation.BaseUnitTest;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidationListenerHandleTest extends BaseUnitTest {
    Property<String> dummyObservable = new SimpleStringProperty();

    FakeInvalidationListener listener = new FakeInvalidationListener();
    InvalidationListenerHandle listenerHandle = new InvalidationListenerHandle(listener);

    @Test
    void can_attach_listener_to_observable() {
        listenerHandle.attachTo(dummyObservable);

        listener.assertCalledTimes(0);

        dummyObservable.setValue("foo");
        dummyObservable.setValue("bar");
        dummyObservable.setValue("nyu");

        listener.assertCalledTimes(3);
    }

    @Test
    void can_detach_listener_from_observable() {
        listenerHandle.attachTo(dummyObservable);
        dummyObservable.setValue("foo");
        dummyObservable.setValue("bar");
        listener.assertCalledTimes(2);

        listenerHandle.detachFrom(dummyObservable);
        dummyObservable.setValue("meh");
        dummyObservable.setValue("heh");
        listener.assertCalledTimes(2);

        listenerHandle.attachTo(dummyObservable);
        dummyObservable.setValue("blah");
        dummyObservable.setValue("yada");
        listener.assertCalledTimes(4);
    }

    @Test
    void detaching_not_attached_listener_does_nothing() {
        listenerHandle.detachFrom(dummyObservable);
        listenerHandle.detachFrom(dummyObservable);
        listenerHandle.detachFrom(dummyObservable);

        listener.assertCalledTimes(0);
    }

    @Test
    void will_not_attach_listener_multiple_times() {
        listenerHandle.attachTo(dummyObservable);
        listenerHandle.attachTo(dummyObservable);
        listenerHandle.attachTo(dummyObservable);

        dummyObservable.setValue("blah");

        listener.assertCalledTimes(1);
    }

    private class FakeInvalidationListener implements InvalidationListener {
        private int callsCount = 0;

        @Override
        public void invalidated(Observable observable) {
            // Force eager evaluation, otherwise we will be notified
            // only once - when the property value changes for the first time.
            ((ObservableValue<?>)observable).getValue();
            callsCount++;
        }

        public void assertCalledTimes(int expectedCallsCount) {
            assertThat(callsCount)
                    .as("Number of times FakeInvalidationListener was called")
                    .isEqualTo(expectedCallsCount);
        }
    }
}