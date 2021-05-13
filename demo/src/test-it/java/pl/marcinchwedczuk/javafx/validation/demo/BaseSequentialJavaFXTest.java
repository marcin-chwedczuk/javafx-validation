package pl.marcinchwedczuk.javafx.validation.demo;

import org.junit.jupiter.api.extension.*;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;
import pl.marcinchwedczuk.javafx.validation.demo.utils.JavaFXTest;
import pl.marcinchwedczuk.javafx.validation.demo.utils.SequentialApplicationExtension;
import pl.marcinchwedczuk.javafx.validation.demo.utils.StopOnFirstFailure;
import pl.marcinchwedczuk.javafx.validation.demo.utils.TakeScreenShotOnFailureExtension;

@ExtendWith({
        SequentialApplicationExtension.class,
        TakeScreenShotOnFailureExtension.class
})
@StopOnFirstFailure
public abstract class BaseSequentialJavaFXTest { }
