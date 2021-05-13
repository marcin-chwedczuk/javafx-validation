package pl.marcinchwedczuk.javafx.validation.demo.utils;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

// https://github.com/junit-team/junit5/issues/48 - Introduce first-class support for scenario tests

class StopOnFirstFailureExtension implements ExecutionCondition, TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        try {
            ExtensionContext.Namespace namespace = namespaceFor(context);
            ExtensionContext.Store store = storeFor(context, namespace);
            store.put(StopOnFirstFailureExtension.class, context.getDisplayName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw throwable;
    }

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        var namespace = namespaceFor(context);
        var store = storeFor(context, namespace);

        var value = store.get(StopOnFirstFailureExtension.class, String.class);
        if (value == null) {
            return ConditionEvaluationResult.enabled("No test failures in stepwise tests");
        } else {
            return ConditionEvaluationResult.disabled(
                    "Stepwise test disabled due to previous failure in '" + value + "'");
        }
    }

    private ExtensionContext.Namespace namespaceFor(ExtensionContext context) {
        return ExtensionContext.Namespace.create(StopOnFirstFailureExtension.class, context.getParent());
    }

    private ExtensionContext.Store storeFor(ExtensionContext context, ExtensionContext.Namespace namespace) {
        return context.getParent().get().getStore(namespace);
    }
}

