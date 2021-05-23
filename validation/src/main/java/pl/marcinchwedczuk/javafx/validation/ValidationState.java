package pl.marcinchwedczuk.javafx.validation;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collector.Characteristics.*;

public enum ValidationState {
    VALID,
    INVALID,
    NOT_RUN;

    public static
    Collector<ValidationState, ?, ValidationState> combineCollector() {
        return Collectors.reducing(ValidationState.VALID, ValidationState::combine);
    }

    private static ValidationState combine(ValidationState s1, ValidationState s2) {
        if (s1 == INVALID || s2 == INVALID) return INVALID;
        if (s1 == NOT_RUN || s2 == NOT_RUN) return NOT_RUN;
        return VALID;
    }
}
