package pl.marcinchwedczuk.javafx.validation.testutils;

import org.junit.jupiter.api.extension.ExtendWith;
import pl.marcinchwedczuk.javafx.validation.testutils.utils.SequentialApplicationExtension;
import pl.marcinchwedczuk.javafx.validation.testutils.utils.StopOnFirstFailure;
import pl.marcinchwedczuk.javafx.validation.testutils.utils.TakeScreenshotOnFailure;

@ExtendWith(SequentialApplicationExtension.class)
@TakeScreenshotOnFailure
@StopOnFirstFailure
public abstract class BaseSequentialJavaFXTest {
}
