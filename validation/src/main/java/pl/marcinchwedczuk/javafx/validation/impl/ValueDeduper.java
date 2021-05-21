package pl.marcinchwedczuk.javafx.validation.impl;

public class ValueDeduper<V> {
    private V prev = null;
    private boolean prevSet = false;

    public boolean checkNewValue(V value) {
        if (!prevSet || !safeEquals(value, prev)) {
            prevSet = true;
            prev = value;
            return true;
        }

        return false;
    }

    private boolean safeEquals(V value1, V value2) {
        if (value1 == null) {
            return (value2 == null);
        }

        return value1.equals(value2);
    }
}
