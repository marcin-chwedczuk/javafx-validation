package pl.marcinchwedczuk.javafx.validation.demo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import pl.marcinchwedczuk.javafx.validation.demo.utils.TakeScreenShotOnFailureExtension;

@ExtendWith({
        ApplicationExtension.class,
        TakeScreenShotOnFailureExtension.class
})
public abstract class BaseJavaFXTest {
}
