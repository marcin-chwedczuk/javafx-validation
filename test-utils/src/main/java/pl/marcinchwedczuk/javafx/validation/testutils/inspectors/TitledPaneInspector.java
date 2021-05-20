package pl.marcinchwedczuk.javafx.validation.testutils.inspectors;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import org.testfx.api.FxRobot;

import static org.testfx.assertions.api.Assertions.assertThat;

public class TitledPaneInspector extends BaseControlInspector {
    public TitledPaneInspector(FxRobot robot, String fxid) {
        super(robot, fxid);
    }

    public TitledPaneInspector assertNotEmpty() {
        TitledPane pane = robot.lookup(idSelector()).queryAs(TitledPane.class);

        assertThat(pane.getContent())
                .as(makeDescription("content property"))
                .isNotNull();

        if (pane.getContent() instanceof Pane) {
            assertThat(((Pane) pane.getContent()).getChildrenUnmodifiable())
                    .as(makeDescription("children"))
                    .isNotEmpty();
        }

        return this;
    }

    private String makeDescription() {
        return makeDescription(null);
    }

    private String makeDescription(String property) {
        return "TitledPane(id=" + fxid + ")" +
                (property == null ? "" : " " + property);
    }
}
