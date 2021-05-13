package pl.marcinchwedczuk.javafx.validation.demo.utils;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({
        ApplicationExtension.class,
        TakeScreenShotOnFailureExtension.class
})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JavaFXTest { }
