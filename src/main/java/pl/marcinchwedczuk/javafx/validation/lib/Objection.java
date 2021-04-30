package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Objection {
    public final String message;
    public final ObjectionSeverity severity;

    Objection(String message, ObjectionSeverity severity) {
        this.message = message;
        this.severity = severity;
    }
}
