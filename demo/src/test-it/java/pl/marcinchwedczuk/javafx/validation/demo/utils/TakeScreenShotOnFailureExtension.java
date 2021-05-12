package pl.marcinchwedczuk.javafx.validation.demo.utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.testfx.api.FxRobot;
import org.testfx.util.DebugUtils;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;


public class TakeScreenShotOnFailureExtension implements TestExecutionExceptionHandler {
    private AtomicInteger nextId = new AtomicInteger(1000);

    @Override
    public void handleTestExecutionException(ExtensionContext context,
                                             Throwable throwable) throws Throwable {
        try {
            String dir = System.getProperty("screenshotDirectory");
            if (dir == null) {
                System.err.println("screenshotDirectory property is not set, skipping taking screenshot!");
                return;
            }

            Files.createDirectories(Paths.get(dir));

            // TODO: Test name should be part of the screenshot
            StringBuilder info = DebugUtils.saveWindow(() ->
                    Paths.get(dir, "screen_" + nextId.getAndIncrement() + ".png"), ""
            ).apply(new StringBuilder());

            System.out.println(info.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw throwable;
    }
}