package pl.marcinchwedczuk.javafx.validation.validators;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import pl.marcinchwedczuk.javafx.validation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators.RangeOptions.ALLOW_EMPTY_RANGE;
import static pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators.RangeOptions.DISALLOW_EMPTY_RANGE;

public class IntegerValidators {
    private IntegerValidators() {
    }

    public static
    ValidatorBuilder.Builder<Integer> between(int min, int maxExcluded) {
        return ValidatorBuilder.<Integer>newValidator()
                .withName("between(%d, %d)", min, maxExcluded)
                .withPredicate(value -> {
                    boolean isValid =
                            (value == null) ||
                            (min <= (Integer)value && (Integer)value < maxExcluded);
                    return isValid;
                })
                .withExplanationVariables(Map.of(
                        "min", min,
                        "max", maxExcluded
                ))
                .withExplanation(Explanation.of(
                        "#{value} must be between #{min} and #{max}."));
    }

    public static
    ValidatorBuilder.Builder<Integer> validRangeWithStart(ObservableValue<Integer> rangeStart, RangeOptions options) {
        Objects.requireNonNull(rangeStart);
        Objects.requireNonNull(options);

        return ValidatorBuilder.<Integer>newValidator()
                .withName("validRangeWithStart(%s, %s)", rangeStart, options)
                .withPredicate(end -> {
                    Integer start = rangeStart.getValue();

                    boolean isValid =
                            (start == null) ||
                            (end == null) ||
                            ((Integer)start < (Integer)end) ||
                            ((options == ALLOW_EMPTY_RANGE) && (start.equals(end)));

                    return isValid;
                })
                .withDependencies(rangeStart)
                .withExplanationVariables(Map.of(
                        "rangeStart", rangeStart,
                        "conditionType", (options == ALLOW_EMPTY_RANGE) ? "greater or equal" : "greater"
                ))
                .withExplanation(Explanation.of(
                        // TODO: Better message
                        "Invalid range of numbers: #{value} (this number) must be #{conditionType} than #{rangeStart}."));
    }

    public enum RangeOptions {
        ALLOW_EMPTY_RANGE,
        DISALLOW_EMPTY_RANGE
    }
}
