package pl.marcinchwedczuk.javafx.validation;

import pl.marcinchwedczuk.javafx.validation.impl.EqualsBuilder;
import pl.marcinchwedczuk.javafx.validation.impl.ToStringBuilder;

import java.util.Objects;

public class Objection {
    public final String userMessage;
    public final ObjectionSeverity severity;

    Objection(String userMessage, ObjectionSeverity severity) {
        this.userMessage = Objects.requireNonNull(userMessage);
        this.severity = Objects.requireNonNull(severity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(Objection.class)
                .appendField("userMessage", userMessage)
                .appendField("severity", severity)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Objection)) { return false; }
        Objection other = (Objection)o;

        return new EqualsBuilder()
                .equals(this.userMessage, other.userMessage)
                .equals(this.severity, other.severity)
                .isEqual();
    }

    @Override
    public int hashCode() {
        return Objects.hash(userMessage, severity);
    }
}
