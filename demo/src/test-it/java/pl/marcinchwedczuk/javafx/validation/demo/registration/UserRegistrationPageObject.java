package pl.marcinchwedczuk.javafx.validation.demo.registration;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import pl.marcinchwedczuk.javafx.validation.demo.BaseJavaFXPageObject;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.*;

import java.util.Objects;

public class UserRegistrationPageObject extends BaseJavaFXPageObject<UserRegistrationPageObject> {
    private final BannerInspector invalidBanner;
    private final TextFieldInspector usernameF;
    private final ValidationDecoratorInspector usernameE;
    private final TextFieldInspector passwordF;
    private final ValidationDecoratorInspector passwordE;
    private final ButtonInspector registerUserButton;

    public UserRegistrationPageObject(FxRobot robot) {
        super(robot, "userRegistrationView");

        invalidBanner = new BannerInspector(robot, "invalidBanner");

        usernameF = new TextFieldInspector(robot, "usernameF");
        usernameE = new ValidationDecoratorInspector(robot, "usernameE");
        passwordF = new TextFieldInspector(robot, "passwordF");
        passwordE = new ValidationDecoratorInspector(robot, "passwordE");

        registerUserButton = new ButtonInspector(robot, "registerUserButton");
    }

    @Override
    protected UserRegistrationPageObject self() { return this; }

    public BannerInspector invalidBanner() {
        return invalidBanner;
    }

    public TextFieldInspector username() {
        return usernameF;
    }

    public ValidationDecoratorInspector usernameErrors() {
        return usernameE;
    }

    public TextFieldInspector password() {
        return passwordF;
    }

    public ValidationDecoratorInspector passwordErrors() {
        return passwordE;
    }

    public UserRegistrationPageObject clickRegisterButton() {
        registerUserButton.click();
        return this;
    }

    public void setupView(Stage stage) {
        TitledPane pane = new TitledPane();
        pane.setCollapsible(false);
        UserRegistration.installAt(pane.contentProperty());

        // Set "synthetic id", for better selectors
        pane.getContent().setId(fxid);

        // Wrap in HBox to avoid sizing issues
        HBox hbox = new HBox();
        hbox.getChildren().add(pane);

        stage.setScene(new Scene(hbox, 500, 500));
        stage.show();
    }
}
