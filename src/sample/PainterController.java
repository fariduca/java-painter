package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PainterController {

    private enum PenSize {
        SMALL(2),
        MEDIUM(4),
        LARGE(6);
        private final int radius;

        PenSize(int radius) { this.radius = radius; }
        public int getRadius() { return radius; }
    }

    @FXML
    private TextField redTextField;

    @FXML
    private TextField greenTextField;

    @FXML
    private TextField blueTextField;

    @FXML
    private TextField alphaTextField;

    @FXML
    private Slider redSlider;

    @FXML
    private Slider greenSlider;

    @FXML
    private Slider blueSlider;

    @FXML
    private Slider alphaSlider;

    @FXML
    private RadioButton smallRadioButton;

    @FXML
    private ToggleGroup sizeToggleGroup;

    @FXML
    private RadioButton mediumRadioButton;

    @FXML
    private RadioButton largeRadioButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button clearButton;

    @FXML
    private Pane drawingAreaPane;

    private PenSize radius = PenSize.MEDIUM;
    private Paint brushColor;
    private int red, green, blue;
    private double alpha;

    public void initialize() {
        smallRadioButton.setUserData(PenSize.SMALL);
        mediumRadioButton.setUserData(PenSize.MEDIUM);
        largeRadioButton.setUserData(PenSize.LARGE);

        mediumRadioButton.setSelected(true);

        redTextField.textProperty().bind(
                redSlider.valueProperty().asString("%.0f"));
        greenTextField.textProperty().bind(
                greenSlider.valueProperty().asString("%.0f"));
        blueTextField.textProperty().bind(
                blueSlider.valueProperty().asString("%.0f"));
        alphaTextField.textProperty().bind(
                alphaSlider.valueProperty().asString("%.2f"));

        red = redSlider.valueProperty().getValue().intValue();
        green = greenSlider.valueProperty().getValue().intValue();
        blue = blueSlider.valueProperty().getValue().intValue();
        alpha = alphaSlider.valueProperty().getValue();
        brushColor = Color.rgb(red, green, blue, alpha);

        redSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                                Number number, Number t1) {
                int red = t1.intValue();
                green = greenSlider.valueProperty().getValue().intValue();
                blue = blueSlider.valueProperty().getValue().intValue();
                alpha = alphaSlider.valueProperty().getValue();
                brushColor = Color.rgb(red, green, blue, alpha);
            }
        });

        greenSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                                Number number, Number t1) {
                int green = t1.intValue();
                red = redSlider.valueProperty().getValue().intValue();
                blue = blueSlider.valueProperty().getValue().intValue();
                alpha = alphaSlider.valueProperty().getValue();
                brushColor = Color.rgb(red, green, blue, alpha);
            }
        });

        blueSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                                Number number, Number t1) {
                int blue = t1.intValue();
                red = redSlider.valueProperty().getValue().intValue();
                green = greenSlider.valueProperty().getValue().intValue();
                alpha = alphaSlider.valueProperty().getValue();
                brushColor = Color.rgb(red, green, blue, alpha);
            }
        });

        alphaSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                                Number number, Number t1) {
                double alpha = t1.doubleValue();
                red = redSlider.valueProperty().getValue().intValue();
                green = greenSlider.valueProperty().getValue().intValue();
                blue = blueSlider.valueProperty().getValue().intValue();
                brushColor = Color.rgb(red, green, blue, alpha);
            }
        });
    }

    @FXML
    void clearButtonPressed(ActionEvent event) {
        drawingAreaPane.getChildren().clear();
    }

    @FXML
    void drawingAreaMouseDragged(MouseEvent event) {
        Circle circle = new Circle(event.getX(), event.getY(),
                radius.getRadius(), brushColor);
        drawingAreaPane.getChildren().add(circle);
    }

    @FXML
    void sizeRadioButtonSelected(ActionEvent event) {
        radius = (PenSize) sizeToggleGroup.getSelectedToggle().getUserData();
    }

    @FXML
    void undoButtonPressed(ActionEvent event) {
        int count = drawingAreaPane.getChildren().size();
        if (count > 0) {
            drawingAreaPane.getChildren().remove(count - 1);
        }
    }
}
