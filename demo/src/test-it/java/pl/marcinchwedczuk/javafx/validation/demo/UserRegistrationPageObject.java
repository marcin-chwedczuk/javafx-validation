package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import pl.marcinchwedczuk.javafx.validation.demo.inspectors.*;
import pl.marcinchwedczuk.javafx.validation.demo.registration.UserRegistration;

import java.util.Objects;

public class UserRegistrationPageObject {
    private final FxRobot robot;

    private final BannerInspector invalidBanner;
    private final TextFieldInspector usernameF;
    private final TextFieldInspector passwordF;
    private final ButtonInspector registerUserButton;

    public UserRegistrationPageObject(FxRobot robot) {
        this.robot = Objects.requireNonNull(robot);

        invalidBanner = new BannerInspector(robot, "#invalidBanner");
        usernameF = new TextFieldInspector(robot, "#usernameF");
        passwordF = new TextFieldInspector(robot, "#passwordF");
        registerUserButton = new ButtonInspector(robot, "#registerUserButton");
    }

    public BannerInspector invalidBanner() {
        return invalidBanner;
    }

    public TextFieldInspector username() {
        return usernameF;
    }

    public TextFieldInspector password() {
        return passwordF;
    }

    public UserRegistrationPageObject clickRegisterButton() {
        registerUserButton.click();
        return this;
    }

    public UserRegistrationPageObject moveFocusToWindow() {
        // We will click in the top-left pixel to move focus from
        // controls back to the window.
        Node banner = robot.lookup("#userRegistrationView").query();

        // Why so complicated?
        Bounds b = banner.getBoundsInLocal();
        Bounds sb = banner.localToScreen(b);
        robot.clickOn(new Point2D(sb.getMinX(), sb.getMinY()));

        return this;
    }

    public void setupView(Stage stage) {
        TitledPane pane = new TitledPane();
        pane.setCollapsible(false);
        UserRegistration.installAt(pane.contentProperty());

        // Wrap in HBox to avoid sizing issues
        HBox hbox = new HBox();
        hbox.getChildren().add(pane);

        stage.setScene(new Scene(hbox, 500, 500));
        stage.show();
    }

}
