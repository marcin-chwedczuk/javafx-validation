package pl.marcinchwedczuk.javafx.validation.lib;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.value.ObservableValue;

import java.util.List;
import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.lib.IntegerValidators.RangeOptions.ALLOW_EMPTY_RANGE;
import static pl.marcinchwedczuk.javafx.validation.lib.IntegerValidators.RangeOptions.NON_EMPTY_RANGE;

public class IntegerValidators {
    private IntegerValidators() {}

    public static Validator<Integer> validRangeWithStart(ObservableValue<Integer> rangeStart, RangeOptions options) {
        Objects.requireNonNull(rangeStart);

        return new Validator<Integer>() {
            @Override
            public ValidationResult<Integer> validate(Integer end) {
                Integer start = rangeStart.getValue();

                boolean isValid =
                        (start == null) ||
                        (end == null) ||
                        (start < end) ||
                        ((options == ALLOW_EMPTY_RANGE) && (start.equals(end)));

                return new ValidationResult<Integer>(end,
                        Objections.errorIf(!isValid,
                                String.format(
                                        "Invalid range of numbers: %d (this number) must be %s than %d.",
                                        end, ((options == NON_EMPTY_RANGE) ? "greater" : "greater or equal"), start)));
            }

            @Override
            public List<Observable> dependencies() {
                return List.of(rangeStart);
            }
        };
    }

    public enum RangeOptions {
        ALLOW_EMPTY_RANGE,
        NON_EMPTY_RANGE
    }
}
