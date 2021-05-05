package pl.marcinchwedczuk.javafx.validation.extras;

import javafx.beans.InvalidationListener;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.marcinchwedczuk.javafx.validation.lib.Objection;
import pl.marcinchwedczuk.javafx.validation.lib.Objections;

import java.util.List;

class ValidationDecoratorSkin extends SkinBase<ValidationDecorator> {
    private static PseudoClass CSS_WITH_ERRORS = PseudoClass.getPseudoClass("with-errors");
    private static PseudoClass CSS_WITH_WARNINGS = PseudoClass.getPseudoClass("with-warnings");

    private final VBox rootNode;
    private final VBox componentContainer;
    private final VBox objectionsContainer;

    protected ValidationDecoratorSkin(ValidationDecorator control) {
        super(control);

        rootNode = new VBox();
        rootNode.getStyleClass().setAll("validation-decorator");

        componentContainer = new VBox();
        componentContainer.getStyleClass().setAll("component-container");

        objectionsContainer = new VBox();
        objectionsContainer.getStyleClass().setAll("objections-container");

        rootNode.getChildren().setAll(componentContainer, objectionsContainer);
        this.getChildren().add(rootNode);

        getSkinnable().objectionsProperty().addListener((InvalidationListener) observable -> {
            updateObjections();
        });
        updateObjections();

        getSkinnable().contentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                componentContainer.getChildren().clear();
            } else {
                componentContainer.getChildren().setAll(newValue);
            }
        });

        Node content = getSkinnable().getContent();
        if (content != null) {
            componentContainer.getChildren().setAll(content);
        }
    }

    private void updateObjections() {
        List<Objection> objections = getSkinnable().objectionsProperty().getValue();

        var messagesFx = objectionsContainer.getChildren();

        if (messagesFx.size() > objections.size()) {
            messagesFx.remove(objections.size(), messagesFx.size());
        }
        while (messagesFx.size() < objections.size()) {
            messagesFx.add(new ValidationMessageFx());
        }

        for (int i = 0; i < objections.size(); i++) {
            ((ValidationMessageFx) messagesFx.get(i)).setObjection(objections.get(i));
        }

        updateComponentCssPsuedoClasses();
    }

    private void updateComponentCssPsuedoClasses() {
        List<Objection> objections = getSkinnable().getObjections();
        boolean containsErrors = Objections.containsError(objections);

        componentContainer.pseudoClassStateChanged(CSS_WITH_ERRORS, containsErrors);
        componentContainer.pseudoClassStateChanged(CSS_WITH_WARNINGS, Objections.containsWarning(objections) && !containsErrors);
    }

    @Override
    protected void layoutChildren(final double x, double y,
                                  final double w, final double h) {
        double ww = snapSizeX(w);
        double hh = snapSizeY(h);
        rootNode.resize(ww, hh);
        positionInArea(rootNode, x, y, ww, hh, /*baseline ignored*/0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double contentWidth = snapSizeX(rootNode.minWidth(height));
        return contentWidth + leftInset + rightInset;
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double contentHeight = rootNode.minHeight(width);
        return snapSizeY(contentHeight) + topInset + bottomInset;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double contentWidth = snapSizeX(rootNode.prefWidth(height));
        return contentWidth + leftInset + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double contentHeight = rootNode.prefHeight(width);
        return snapSizeY(contentHeight) + topInset + bottomInset;
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return Double.MAX_VALUE;
    }

    public static class ValidationMessageFx extends HBox {
        private final Circle circle;
        private final Label text;

        public ValidationMessageFx() {
            super.setSpacing(5);
            super.setAlignment(Pos.BASELINE_LEFT);

            this.circle = new Circle(5.0, Color.BLACK);
            this.circle.getStyleClass().setAll("marker");

            this.text = new Label();
            this.text.setWrapText(true);
            this.text.setTooltip(new Tooltip(""));
            this.text.getStyleClass().setAll("message");

            this.getChildren().addAll(circle, text);
            this.getStyleClass().add("objection");
        }

        public void setObjection(Objection e) {
            this.text.setText(e.userMessage);
            this.text.getTooltip().setText(e.userMessage);
            this.getStyleClass().setAll("objection", "severity-" + e.severity.toString().toLowerCase());
        }
    }
}
