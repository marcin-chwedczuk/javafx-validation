package pl.marcinchwedczuk.javafx.validation.lib;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Objections {
    private Objections() { }

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

    @SafeVarargs
    public static List<Objection> combine(List<Objection>... errors) {
        return Arrays.stream(errors)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    public static Comparator<Objection> compareBySeverity() {
        return Comparator.comparingInt(o -> o.severity.priority);
    }
}
