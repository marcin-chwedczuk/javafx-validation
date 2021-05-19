package pl.marcinchwedczuk.javafx.validation.testutils.inspectors;

import org.testfx.api.FxRobot;

import java.util.Objects;

public class BaseControlInspector {
    protected final FxRobot robot;
    protected final String fxid;

    public BaseControlInspector(FxRobot robot, String fxid) {
        this.robot = Objects.requireNonNull(robot);
        this.fxid = Objects.requireNonNull(fxid);
    }

    protected String idSelector() {
        return "#" + fxid;
    }

    protected String selectWithId(String cssQuery) {
        return String.format("#%s %s", fxid, cssQuery);
    }
}
