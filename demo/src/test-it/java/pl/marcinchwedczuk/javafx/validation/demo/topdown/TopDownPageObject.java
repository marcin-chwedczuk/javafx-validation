package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.BannerInspector;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.ValidationDecoratorInspector;
import pl.marcinchwedczuk.javafx.validation.testutils.BaseJavaFXPageObject;
import pl.marcinchwedczuk.javafx.validation.testutils.inspectors.*;

public class TopDownPageObject extends BaseJavaFXPageObject<TopDownPageObject> {
    private final BannerInspector invalidBanner;

    private final ComboBoxInspector<Country> countryF;
    private final ValidationDecoratorInspector countryE;

    private final TextFieldInspector mobileF;
    private final ValidationDecoratorInspector mobileE;

    private final TextFieldInspector faxF;
    private final ValidationDecoratorInspector faxE;

    private final ButtonInspector validateButton;

    public TopDownPageObject(FxRobot robot) {
        super(robot, "topDownView");

        invalidBanner = new BannerInspector(robot, "invalidBanner");

        countryF = new ComboBoxInspector<>(robot, "countryF");
        countryE = new ValidationDecoratorInspector(robot, "countryE");

        mobileF = new TextFieldInspector(robot, "mobileF");
        mobileE = new ValidationDecoratorInspector(robot, "mobileE");

        faxF = new TextFieldInspector(robot, "faxF");
        faxE = new ValidationDecoratorInspector(robot, "faxE");

        validateButton = new ButtonInspector(robot, "validateButton");
    }

    @Override
    protected TopDownPageObject self() { return this; }

    public BannerInspector invalidBanner() {
        return invalidBanner;
    }

    public ComboBoxInspector<Country> country() {
        return countryF;
    }

    public ValidationDecoratorInspector countryErrors() {
        return countryE;
    }

    public TextFieldInspector mobile() {
        return mobileF;
    }

    public ValidationDecoratorInspector mobileErrors() {
        return mobileE;
    }

    public TextFieldInspector fax() {
        return faxF;
    }

    public ValidationDecoratorInspector faxErrors() {
        return faxE;
    }

    public void clickValidateButton() {
        validateButton.click();
    }

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
