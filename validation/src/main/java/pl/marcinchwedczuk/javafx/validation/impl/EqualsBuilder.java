package pl.marcinchwedczuk.javafx.validation.impl;

public class EqualsBuilder {
    private boolean isEqual = true;

    public EqualsBuilder equals(String a, String b) {
        if (!isEqual) { return this; }

        isEqual = (a == null && b == null) ||
                  ((a != null) && a.equals(b));
        return this;
    }

    public <E extends Enum<E>>
    EqualsBuilder equals(E a, E b) {
        if (!isEqual) { return this; }

        isEqual = (a == b);
        return this;
    }

    public boolean isEqual() {
        return isEqual;
    }
}
