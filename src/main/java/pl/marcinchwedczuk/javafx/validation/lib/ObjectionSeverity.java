package pl.marcinchwedczuk.javafx.validation.lib;

public enum ObjectionSeverity {
    ERROR(100),
    WARNING(50);

    public final int priority;

    ObjectionSeverity(int priority) {
        this.priority = priority;
    }
}
