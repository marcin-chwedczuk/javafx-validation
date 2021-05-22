package pl.marcinchwedczuk.javafx.validation;

public enum ValidationState {
    VALID,
    INVALID,
    NOT_RUN;

    public static ValidationState combine(ValidationState... states) {
        ValidationState result = VALID;
        for (ValidationState state : states) {
            result = combine(result, state);
        }
        return result;
    }

    public static ValidationState combine(ValidationState s1, ValidationState s2) {
        if (s1 == INVALID || s2 == INVALID) return INVALID;
        if (s1 == NOT_RUN || s2 == NOT_RUN) return NOT_RUN;
        return VALID;
    }
}
