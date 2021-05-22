package pl.marcinchwedczuk.javafx.validation.validators;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import pl.marcinchwedczuk.javafx.validation.Objections;
import pl.marcinchwedczuk.javafx.validation.ValidationResult;
import pl.marcinchwedczuk.javafx.validation.Validator;

import java.util.List;
import java.util.Objects;

import static pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators.RangeOptions.ALLOW_EMPTY_RANGE;
import static pl.marcinchwedczuk.javafx.validation.validators.IntegerValidators.RangeOptions.DISALLOW_EMPTY_RANGE;

public class IntegerValidators {
    private IntegerValidators() {
    }

    public static Validator<Integer> validRangeWithStart(ObservableValue<Integer> rangeStart, RangeOptions options) {
        Objects.requireNonNull(rangeStart);
        Objects.requireNonNull(options);

        return new Validator<>() {
            @Override
            public <TT extends Integer> ValidationResult<TT> validate(TT end) {
                Integer start = rangeStart.getValue();

                boolean isValid =
                        (start == null) ||
                        (end == null) ||
                        ((Integer)start < (Integer)end) ||
                        ((options == ALLOW_EMPTY_RANGE) && (start.equals(end)));

                if (isValid) {
                    return ValidationResult.success(end);
                } else {
                    return ValidationResult.failure(end,
                            Objections.error(String.format(
                                    "Invalid range of numbers: %d (this number) must be %s than %d.",
                                    end, ((options == DISALLOW_EMPTY_RANGE) ? "greater" : "greater or equal"), start)));
                }
            }

            @Override
            public List<Observable> dependencies() {
                return List.of(rangeStart);
            }
        };
    }

    public enum RangeOptions {
        ALLOW_EMPTY_RANGE,
        DISALLOW_EMPTY_RANGE
    }
}
