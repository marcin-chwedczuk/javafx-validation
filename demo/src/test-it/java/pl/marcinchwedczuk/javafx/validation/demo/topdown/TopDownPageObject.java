package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import pl.marcinchwedczuk.javafx.validation.demo.BaseJavaFXPageObject;
import pl.marcinchwedczuk.javafx.validation.demo.range.NumberRange;

public class TopDownPageObject extends BaseJavaFXPageObject<TopDownPageObject> {
    public TopDownPageObject(FxRobot robot) {
        super(robot, "topDownView");
    }

    @Override
    protected TopDownPageObject self() { return this; }

    public void setupView(Stage stage) {
        TitledPane pane = new TitledPane();
        pane.setCollapsible(false);
        TopDownView.installAt(pane.contentProperty());

        // Set "synthetic id", for better selectors
        pane.getContent().setId(fxid);

        // Wrap in HBox to avoid sizing issues
        HBox hbox = new HBox();
        hbox.getChildren().add(pane);

        stage.setScene(new Scene(hbox, 500, 500));
        stage.show();
    }
}
