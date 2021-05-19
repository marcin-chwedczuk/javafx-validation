package pl.marcinchwedczuk.javafx.validation.demo.range;

import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.BannerInspector;
import pl.marcinchwedczuk.javafx.validation.testutils.BaseJavaFXPageObject;
import pl.marcinchwedczuk.javafx.validation.testutils.inspectors.ButtonInspector;
import pl.marcinchwedczuk.javafx.validation.testutils.inspectors.TextFieldInspector;
import pl.marcinchwedczuk.javafx.validation.testutils.inspectors.ValidationDecoratorInspector;

public class NumberRangePageObject extends BaseJavaFXPageObject<NumberRangePageObject> {
    private final BannerInspector invalidBanner;

    private final TextFieldInspector fromF;
    private final ValidationDecoratorInspector fromE;

    private final TextFieldInspector toF;
    private final ValidationDecoratorInspector toE;

    private final ButtonInspector validateButton;

    public NumberRangePageObject(FxRobot robot) {
        super(robot, "numberRange");

        invalidBanner = new BannerInspector(robot, "invalidBanner");

        fromF = new TextFieldInspector(robot, "fromF");
        fromE = new ValidationDecoratorInspector(robot, "fromE");

        toF = new TextFieldInspector(robot, "toF");
        toE = new ValidationDecoratorInspector(robot, "toE");

        validateButton = new ButtonInspector(robot, "validateButton");
    }

    @Override
    protected NumberRangePageObject self() { return this; }

    public BannerInspector invalidBanner() {
        return invalidBanner;
    }

    public TextFieldInspector from() {
        return fromF;
    }

    public ValidationDecoratorInspector fromErrors() {
        return fromE;
    }

    public TextFieldInspector to() {
        return toF;
    }

    public ValidationDecoratorInspector toErrors() {
        return toE;
    }

    public void clickValidateButton() {
        validateButton.click();
    }

    public void setupView(Stage stage) {
        TitledPane pane = new TitledPane();
        pane.setCollapsible(false);
        NumberRange.installAt(pane.contentProperty());

        // Set "synthetic id", for better selectors
        pane.getContent().setId(fxid);

        // Wrap in HBox to avoid sizing issues
        HBox hbox = new HBox();
        hbox.getChildren().add(pane);

        stage.setScene(new Scene(hbox, 500, 500));
        stage.show();
    }
}
