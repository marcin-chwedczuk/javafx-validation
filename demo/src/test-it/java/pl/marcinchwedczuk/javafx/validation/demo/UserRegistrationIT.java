package pl.marcinchwedczuk.javafx.validation.demo;

import javafx.scene.control.Dialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.testfx.util.DebugUtils;
import pl.marcinchwedczuk.javafx.validation.demo.registration.UserRegistration;

@ExtendWith(ApplicationExtension.class)
public class UserRegistrationIT {

    @Start
    private void start(Stage stage) {
        HBox hbox = new HBox();

        TitledPane pane = new TitledPane();
        pane.setCollapsible(false);
        hbox.getChildren().add(pane);

        UserRegistration.installAt(pane.contentProperty());

        stage.setScene(new Scene(hbox, 500, 500));
        stage.show();
    }

    @Test
    void should_contain_button_with_text(FxRobot robot) throws InterruptedException {
        robot.clickOn("#usernameF").write("mike87");
        robot.clickOn("#passwordF").write("aBcD3fGh1@");
        robot.clickOn("#registerUserButton");

        FxAssert.verifyThat("#info-dialog .content", LabeledMatchers.hasText(new BaseMatcher<String>() {
                                                                    @Override
                                                                    public boolean matches(Object item) {
                                                                        return ((String)item).contains("Registering user");
                                                                    }

                                                                    @Override
                                                                    public void describeTo(Description description) {
                                                                        description.appendText("contains valid");
                                                                    }
                                                                }), DebugUtils.saveWindow());

                robot.clickOn("OK");
    }
}