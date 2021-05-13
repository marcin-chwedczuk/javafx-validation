package pl.marcinchwedczuk.javafx.validation.demo;

import org.junit.jupiter.api.extension.*;
import pl.marcinchwedczuk.javafx.validation.demo.utils.SequentialApplicationExtension;
import pl.marcinchwedczuk.javafx.validation.demo.utils.StopOnFirstFailure;
import pl.marcinchwedczuk.javafx.validation.demo.utils.TakeScreenShotOnFailureExtension;
import pl.marcinchwedczuk.javafx.validation.demo.utils.TakeScreenshotOnFailure;

@ExtendWith(SequentialApplicationExtension.class)
@TakeScreenshotOnFailure
@StopOnFirstFailure
public abstract class BaseSequentialJavaFXTest { }
