package pl.marcinchwedczuk.javafx.validation.demo.inspectors;

import org.assertj.core.api.Condition;
import org.testfx.api.FxRobot;
import pl.marcinchwedczuk.javafx.validation.demo.controls.Banner;
import pl.marcinchwedczuk.javafx.validation.testutils.inspectors.BaseControlInspector;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class BannerInspector extends BaseControlInspector {
    public BannerInspector(FxRobot robot, String fxid) {
        super(robot, fxid);
    }

    public BannerInspector assertVisible() {
        return assertVisible(true);
    }

    public BannerInspector assertHidden() {
        return assertVisible(false);
    }

    public BannerInspector assertVisible(boolean visible) {
        boolean bannerVisible = robot.lookup(idSelector())
                .query()
                .isVisible();

        assertThat(bannerVisible)
                .as(makeDescription("isVisible"))
                .isEqualTo(visible);

        return this;
    }

    public void assertHasText(String text) {
        Banner banner = robot.lookup(idSelector())
                .queryAs(Banner.class);

        assertThat(banner)
                .as(makeDescription("text '" + banner.getText() + "'"))
                .has(new Condition<>(b -> Objects.equals(text, b.getText()), "text '%s'", text));
    }

    private String makeDescription(String property) {
        return "Banner(id=" + fxid + ") " + property;
    }
}
