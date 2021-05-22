package pl.marcinchwedczuk.javafx.validation;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Objections {
    private Objections() {
    }

    public static List<Objection> errorIf(boolean condition, String message) {
        return condition
                ? List.of(error(message))
                : List.of();
    }

    public static Objection error(String message) {
        return error("%s", message);
    }

    public static Objection error(String format, Object... args) {
        return new Objection(
                String.format(format, args), ObjectionSeverity.ERROR);
    }

    public static List<Objection> warningIf(boolean condition, String message) {
        return condition
                ? List.of(warning(message))
                : List.of();
    }

    public static Objection warning(String message) {
        return warning("%s", message);
    }

    public static Objection warning(String format, Object... args) {
        return new Objection(
                String.format(format, args), ObjectionSeverity.WARNING);
    }

    public static Comparator<Objection> compareBySeverityDesc() {
        return Comparator.<Objection>comparingInt(o -> o.severity.priority).reversed();
    }
}
