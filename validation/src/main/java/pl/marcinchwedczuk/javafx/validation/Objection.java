package pl.marcinchwedczuk.javafx.validation;

public class Objection {
    public final String userMessage;
    public final ObjectionSeverity severity;

    Objection(String userMessage, ObjectionSeverity severity) {
        this.userMessage = userMessage;
        this.severity = severity;
    }
}