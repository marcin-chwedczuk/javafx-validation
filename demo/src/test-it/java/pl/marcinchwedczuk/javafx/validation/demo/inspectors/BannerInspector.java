package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import org.assertj.core.api.Condition;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import pl.marcinchwedczuk.javafx.validation.demo.controls.Banner;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class BannerInspector extends BaseControlInspector {
    public BannerInspector(FxRobot robot, String fxid) {
        super(robot, fxid);
    }

    public BannerInspector assertIsVisible() {
        return assertVisible(true);
    }

    public BannerInspector assertIsHidden() {
        return assertVisible(false);
    }

    public BannerInspector assertVisible(boolean visible) {
        boolean bannerVisible = robot.lookup(idSelector())
                .query()
                .isVisible();

        assertThat(bannerVisible)
                .as("Banner[" + fxid + "] should be " + (visible ? "visible" : "hidden") + ".")
                .isEqualTo(visible);

        return this;
    }

    public void assertHasText(String text) {
        Banner banner = robot.lookup(idSelector())
                .queryAs(Banner.class);

        assertThat(banner)
                .as("Banner[" + fxid + "] with text '" + banner.getText() + "'")
                .has(new Condition<>(b -> text.equals(b.getText()), "text '%s'", text));
    }
}
