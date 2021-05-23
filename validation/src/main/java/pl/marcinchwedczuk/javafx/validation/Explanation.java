package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.value.ObservableValue;

import java.util.Objects;

public abstract class Explanation {
    public static Explanation of(String message) {
        return new SimpleExplanation(message);
    }

    public static Explanation of(String format, Object... args) {
        return new FormatStringExplanation(format, args);
    }

    protected abstract String simpleExplain();

    public final String explain() {
        // TODO: Add substituion of special values
        // like #{value}
        // TODO: Extend builder to allow validators specify thier
        // own values like range #{start} #{end}
        return simpleExplain();
    }

    private static class SimpleExplanation extends Explanation {
        private final String message;

        private SimpleExplanation(String message) {
            this.message = Objects.requireNonNull(message);
        }

        @Override
        protected String simpleExplain() {
            return message;
        }
    }

    private static class FormatStringExplanation extends Explanation {
        private final String format;
        private final Object[] args;

        public FormatStringExplanation(String format, Object[] args) {
            this.format = Objects.requireNonNull(format);
            this.args = Objects.requireNonNull(args);
        }

        @Override
        protected String simpleExplain() {
            Object[] actualArgs = new Object[args.length];

            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof ObservableValue<?>) {
                    actualArgs[i] = ((ObservableValue<?>)args[i]).getValue();
                } else {
                    actualArgs[i] = args[i];
                }
            }

            return String.format(format, actualArgs);
        }
    }
}
