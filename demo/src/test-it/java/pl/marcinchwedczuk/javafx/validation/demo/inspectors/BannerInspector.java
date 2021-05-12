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

    public void assertNotVisible() {
        boolean bannerVisible = robot.lookup(fxid + ".banner")
                .query()
                .isVisible();

        assertThat(bannerVisible)
                .as("banner " + fxid + " is visible")
                .isFalse();
    }

}
