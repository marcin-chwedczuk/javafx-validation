package pl.marcinchwedczuk.javafx.validation.impl;

import javafx.beans.value.ObservableValue;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {
    private static final Pattern VARIABLE = Pattern.compile(
            "\\#\\{(?<var>[a-zA-Z_][a-zA-Z0-9_-]*)\\}");

    private final String template;

    public Template(String template) {
        this.template = Objects.requireNonNull(template);
    }

    public String render(Object validatedValue, Map<String, Object> variables) {
        // TODO: Handle observable values
        return replace(
                template,
                variableName -> {
                    if ("value".equals(variableName)) {
                        return Objects.toString(validatedValue);
                    } else {
                        Object value = variables.get(variableName);
                        if (value instanceof ObservableValue<?>) {
                            value = ((ObservableValue<?>)value).getValue();
                        }
                        return Objects.toString(value);
                    }
                });
    }

    private static String replace(String input, Function<String, String> replacer) {
        StringBuilder resultString = new StringBuilder();

        Matcher regexMatcher = VARIABLE.matcher(input);
        while (regexMatcher.find()) {
            String variableName = regexMatcher.group("var");
            regexMatcher.appendReplacement(resultString, replacer.apply(variableName));
        }
        regexMatcher.appendTail(resultString);

        return resultString.toString();
    }
}
