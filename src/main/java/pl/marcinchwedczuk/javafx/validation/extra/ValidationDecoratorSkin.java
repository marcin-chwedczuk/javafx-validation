package pl.marcinchwedczuk.javafx.validation.extra;

import javafx.beans.InvalidationListener;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import pl.marcinchwedczuk.javafx.validation.lib.Objection;
import pl.marcinchwedczuk.javafx.validation.lib.ObjectionSeverity;

import java.util.List;

class ValidationDecoratorSkin extends SkinBase<ValidationDecorator2> {
    private final VBox root;
    private final VBox decoratedComponentsContainer;
    private final VBox validationMessagesContainer;

    protected ValidationDecoratorSkin(ValidationDecorator2 control) {
        super(control);

        root = new VBox();
        root.setPrefHeight(VBox.USE_COMPUTED_SIZE);
        root.setPrefWidth(VBox.USE_COMPUTED_SIZE);

        decoratedComponentsContainer = new VBox();
        decoratedComponentsContainer.setPrefHeight(VBox.USE_COMPUTED_SIZE);
        decoratedComponentsContainer.setPrefWidth(VBox.USE_COMPUTED_SIZE);
        decoratedComponentsContainer.getStyleClass().setAll("decorated");

        validationMessagesContainer = new VBox();
        validationMessagesContainer.setPrefHeight(VBox.USE_COMPUTED_SIZE);
        validationMessagesContainer.setPrefWidth(VBox.USE_COMPUTED_SIZE);
        validationMessagesContainer.getStyleClass().setAll("validationMessages");
        validationMessagesContainer.setSpacing(2);

        root.getChildren().setAll(decoratedComponentsContainer, validationMessagesContainer);
        this.getChildren().add(root);

        getSkinnable().objectionsProperty().addListener((InvalidationListener) observable -> {
            List<Objection> objections = getSkinnable().objectionsProperty().getValue();

            var messagesFx = validationMessagesContainer.getChildren();

            if (messagesFx.size() > objections.size()) {
                messagesFx.remove(objections.size(), messagesFx.size());
            }
            while (messagesFx.size() < objections.size()) {
                messagesFx.add(new ValidationMessageFx());
            }

            for (int i = 0; i < objections.size(); i++) {
                ((ValidationMessageFx) messagesFx.get(i)).setObjection(objections.get(i));
            }

        });

        getSkinnable().contentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                decoratedComponentsContainer.getChildren().clear();
            } else {
                decoratedComponentsContainer.getChildren().setAll(newValue);
            }
        });

        Node content = getSkinnable().getContent();
        if (content != null) {
            decoratedComponentsContainer.getChildren().setAll(content);
        }
    }

    @Override
    protected void layoutChildren(final double x, double y,
                                  final double w, final double h) {
        double ww = snapSizeX(w);
        double hh = snapSizeY(h);
        root.resize(ww, hh);
        positionInArea(root, x, y, ww, hh, /*baseline ignored*/0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double contentWidth = snapSizeX(root.minWidth(height));
        return contentWidth + leftInset + rightInset;
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double contentHeight = root.minHeight(width);
        return snapSizeY(contentHeight) + topInset + bottomInset;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double contentWidth = snapSizeX(root.prefWidth(height));
        return contentWidth + leftInset + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double contentHeight = root.prefHeight(width);
        return snapSizeY(contentHeight) + topInset + bottomInset;
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return Double.MAX_VALUE;
    }

    public static class ValidationMessageFx extends HBox {
        private final Circle circle;
        private final Text text;

        public ValidationMessageFx() {
            super.setSpacing(5);
            super.setAlignment(Pos.BASELINE_LEFT);

            this.circle = new Circle(5.0, Color.RED);
            this.text = new Text();

            this.getChildren().addAll(circle, text);
        }

        public void setObjection(Objection e) {
            this.text.setText("!!!" + e.message);
            // TODO: To styles
            this.circle.setFill(e.severity == ObjectionSeverity.ERROR ? Color.RED : Color.ORANGE);
        }
    }
}
