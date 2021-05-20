package pl.marcinchwedczuk.javafx.validation;

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
        return "Objection{" +
                "userMessage='" + userMessage + '\'' +
                ", severity=" + severity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objection objection = (Objection) o;
        return userMessage.equals(objection.userMessage) &&
                severity == objection.severity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userMessage, severity);
    }
}
