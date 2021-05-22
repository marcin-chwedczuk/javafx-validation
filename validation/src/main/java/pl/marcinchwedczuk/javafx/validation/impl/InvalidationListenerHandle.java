package pl.marcinchwedczuk.javafx.validation.impl;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.Objects;

/**
 * Inspired by: https://nipafx.dev/java-listenerhandles/
 */
public class InvalidationListenerHandle {
    private final InvalidationListener listener;

    public InvalidationListenerHandle(InvalidationListener listener) {
        this.listener = Objects.requireNonNull(listener);
    }

    public InvalidationListenerHandle(Runnable r) {
        this(ignoredArgument -> r.run());
    }

    public void attachTo(Observable observable) {
        // To avoid duplicated first, we remove listener if it's already attached.
        observable.removeListener(listener);
        observable.addListener(listener);
    }

    public void detachFrom(Observable observable) {
        observable.removeListener(listener);
    }
}
