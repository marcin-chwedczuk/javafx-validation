package pl.marcinchwedczuk.javafx.validation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

public class ObjectionsList implements Iterable<Objection> {
    public static final ObjectionsList EMPTY = new ObjectionsList(List.of());

    private final List<Objection> objections;

    public ObjectionsList(Collection<Objection> objections) {
        this.objections = List.copyOf(objections);
    }

    public int size() { return objections.size(); }
    public Objection get(int index) { return objections.get(index); }

    @Override
    public Iterator<Objection> iterator() {
        return objections.iterator();
    }

    public Stream<Objection> stream() {
        return objections.stream();
    }

    public List<Objection> asList() {
        // List is immutable so it is safe
        return objections;
    }

    public ObjectionsList ofSeverity(ObjectionSeverity first, ObjectionSeverity... rest) {
        EnumSet<ObjectionSeverity> severities = EnumSet.of(first, rest);

        return new ObjectionsList(
            objections.stream()
                .filter(o -> severities.contains(o.severity))
                .collect(toUnmodifiableList()));
    }

    public boolean containsError() {
        return containsObjectionOfSeverity(ObjectionSeverity.ERROR);
    }

    public boolean containsWarning() {
        return containsObjectionOfSeverity(ObjectionSeverity.WARNING);
    }

    public boolean containsObjectionOfSeverity(ObjectionSeverity severity) {
        return objections.stream()
                .anyMatch(o -> o.severity == severity);
    }

    public ObjectionsList copySortedBySeverity() {
        return new ObjectionsList(
            stream()
                .sorted(Objections.compareBySeverityDesc())
                .collect(toUnmodifiableList())
        );
    }

    public static ObjectionsList of(Objection first, Objection... rest) {
        List<Objection> tmp = new ArrayList<>();
        tmp.add(first);
        tmp.addAll(Arrays.asList(rest));
        return new ObjectionsList(tmp);
    }
}
