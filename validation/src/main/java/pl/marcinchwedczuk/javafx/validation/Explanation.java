package pl.marcinchwedczuk.javafx.validation;

import javafx.beans.value.ObservableValue;
import pl.marcinchwedczuk.javafx.validation.impl.Template;

import java.util.Map;
import java.util.Objects;

public abstract class Explanation {
    public static Explanation of(String message) {
        return new SimpleExplanation(message);
    }

    public static Explanation of(String format, Object... args) {
        return new FormatStringExplanation(format, args);
    }

    protected abstract String getExplainTemplate();

    public final String explain(Object validatedValue, Map<String, Object> variables) {
        String templateText = getExplainTemplate();
        return new Template(templateText).render(validatedValue, variables);
    }

    private static class SimpleExplanation extends Explanation {
        private final String message;

        private SimpleExplanation(String message) {
            this.message = Objects.requireNonNull(message);
        }

        @Override
        protected String getExplainTemplate() {
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
        protected String getExplainTemplate() {
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
