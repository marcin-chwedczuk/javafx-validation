package pl.marcinchwedczuk.javafx.validation.testutils.utils;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Implementes sequential TestFX tests. This means tests are
 * executed in order, reusing current Stage state.
 */
public class SequentialApplicationExtension extends ApplicationExtension
    implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        super.beforeEach(extensionContext);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        // Suppress JavaFX Stage initialization per test
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        // Suppress JavaFX Stage cleanup per test

        // Required to wait for the end of the UI events processing
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        super.afterEach(extensionContext);
    }
}
