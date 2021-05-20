package pl.marcinchwedczuk.javafx.validation.demo;

import org.testfx.api.FxRobot;
import pl.marcinchwedczuk.javafx.validation.testutils.BaseJavaFXPageObject;
import pl.marcinchwedczuk.javafx.validation.testutils.inspectors.TitledPaneInspector;

public class DemoPageObject extends BaseJavaFXPageObject<DemoPageObject> {
    private final TitledPaneInspector userRegistrationPane;
    private final TitledPaneInspector numberRangePane;
    private final TitledPaneInspector topDownPane;

    public DemoPageObject(FxRobot robot) {
        super(robot, "mainWindow");

        userRegistrationPane = new TitledPaneInspector(robot, "userRegistrationPane");
        numberRangePane = new TitledPaneInspector(robot, "numberRangePane");
        topDownPane = new TitledPaneInspector(robot, "topDownPane");
    }

    public TitledPaneInspector userRegistrationPane() {
        return userRegistrationPane;
    }

    public TitledPaneInspector numberRangePane() {
        return numberRangePane;
    }

    public TitledPaneInspector topDownPane() {
        return topDownPane;
    }

    @Override
    protected DemoPageObject self() {
        return this;
    }
}
