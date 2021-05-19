package pl.marcinchwedczuk.javafx.validation.testutils;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.testfx.api.FxRobot;

import java.util.Objects;

public abstract class BaseJavaFXPageObject<SELF extends BaseJavaFXPageObject<SELF>> {
    protected final FxRobot robot;
    protected final String fxid;

    public BaseJavaFXPageObject(FxRobot robot, String fxid) {
        this.robot = Objects.requireNonNull(robot);
        this.fxid = Objects.requireNonNull(fxid);
    }

    protected abstract SELF self();

    protected String idSelector() {
        return "#" + fxid;
    }

    public SELF moveFocusToWindow() {
        // We will click in the top-left pixel to move focus from
        // controls back to the window.
        Node banner = robot.lookup(idSelector()).query();

        // Why so complicated?
        Bounds b = banner.getBoundsInLocal();
        Bounds sb = banner.localToScreen(b);
        robot.clickOn(new Point2D(sb.getMinX(), sb.getMinY()));

        return self();
    }
}
