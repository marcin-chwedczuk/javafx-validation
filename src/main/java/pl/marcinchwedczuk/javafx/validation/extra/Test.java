package pl.marcinchwedczuk.javafx.validation.extra;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Random;

class ControlSkinPrototype extends SkinBase<ControlPrototype> {
    private static final int LINES_COUNT = 64;
    private static final double LEFT_MARGIN = 2.0;
    private static final double RIGHT_MARGIN = 2.0;
    private static final double MARGINS = LEFT_MARGIN + RIGHT_MARGIN;
    private static final double MIN_LINE_WIDTH = 2.0;
    private static final double MIN_MAIN_WIDTH = MIN_LINE_WIDTH * LINES_COUNT;
    private final Line[] lines = new Line[LINES_COUNT];
    private final double[] values = new double[LINES_COUNT];

    public ControlSkinPrototype(ControlPrototype control) {
        super(control);
        initLinesAndValues();
        initGraphics(control);
    }

    private void initLinesAndValues() {
        final Random random = new Random();
        for (int i = 0; i < LINES_COUNT; ++i) {
            lines[i] = new Line();
            values[i] = random.nextDouble();
        }
    }

    private void initGraphics(ControlPrototype control) {
        control.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        getChildren().addAll(lines);
    }

    @Override
    protected double computeMinWidth(double height,
                                     double topInset, double rightInset,
                                     double bottomInset, double leftInset) {
        return leftInset + MIN_MAIN_WIDTH + MARGINS + rightInset;
    }

    @Override
    protected void layoutChildren(double contentX, double contentY,
                                  double contentWidth, double contentHeight) {
        final double mainWidth = contentWidth - MARGINS,
                lineWidth = mainWidth / LINES_COUNT;
        final double mainX = contentX + LEFT_MARGIN;
        for (int i = 0; i < LINES_COUNT; ++i) {
            final Line line = lines[i];
            final double value = values[i];
            line.setStartX(0);
            line.setStartY(contentHeight);
            line.setEndX(0);
            line.setEndY(contentHeight - value * contentHeight);
            line.setStrokeWidth(lineWidth / 2.0);
            final double lineX = mainX + i * lineWidth;
            layoutInArea(line, lineX, contentY,
                    lineWidth, contentHeight, -1,
                    HPos.CENTER, VPos.BOTTOM);
        }
    }
}
