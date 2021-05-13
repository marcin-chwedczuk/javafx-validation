package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class BannerInspector {
    private final FxRobot robot;
    private final String fxid;

    public BannerInspector(FxRobot robot, String fxid) {
        this.robot = Objects.requireNonNull(robot);
        this.fxid = Objects.requireNonNull(fxid);
    }

    public void assertIsVisible() {
        assertVisible(true);
    }

    public void assertIsHidden() {
        assertVisible(false);
    }

    public void assertVisible(boolean visible) {
        boolean bannerVisible = robot.lookup(fxid + ".banner")
                .query()
                .isVisible();

        assertThat(bannerVisible)
                .as("Banner[" + fxid + "] should be " + (visible ? "visible" : "hidden") + ".")
                .isEqualTo(visible);
    }
}
