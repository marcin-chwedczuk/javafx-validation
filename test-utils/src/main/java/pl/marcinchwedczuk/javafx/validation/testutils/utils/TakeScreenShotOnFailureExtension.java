package pl.marcinchwedczuk.javafx.validation.testutils.utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.testfx.util.DebugUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class TakeScreenShotOnFailureExtension implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext context,
                                             Throwable throwable) throws Throwable {
        try {
            String screenshotsDirProperty = System.getProperty("screenshotDirectory");
            if (screenshotsDirProperty == null) {
                System.err.println("screenshotDirectory property is not set, skipping taking screenshot!");
                return;
            }

            Path screenshotsDir = Paths.get(screenshotsDirProperty);
            Files.createDirectories(screenshotsDir);

            String screenshotName = getScreenshotName(context);
            Path fullScreenshotPath = screenshotsDir.resolve(screenshotName);

            // Use the original method from TestFX, looks weird...
            StringBuilder info = DebugUtils
                    .saveWindow(() -> fullScreenshotPath, "")
                    .apply(new StringBuilder());

            // Print screenshot filename
            System.out.println(info.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw throwable;
    }

    private String getScreenshotName(ExtensionContext context) {
        String testClass = context.getRequiredTestClass().getName();
        String testMethod = context.getRequiredTestMethod().getName();
        return String.format("%s-%s.png", testClass, testMethod);
    }
}