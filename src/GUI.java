import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends BorderPane {

    private Group3D group3D;

    private HBox topBorder;

    public GUI(Stage stage) {
        topBorder = addTopBorder(stage);
        this.setTop(topBorder);
    }

    public void setGroup3D(Group3D group3D) {
        this.group3D = group3D;
    }

    private HBox addTopBorder(Stage stage) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setSpacing(10);
        hBox.prefWidthProperty().bind(stage.widthProperty());
        hBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        hBox.getChildren().addAll(
                addPlayButton(),
                addSpeedOptions(),
                addToggleOptions()
        );
        return hBox;
    }

    private Button addPlayButton() {
        Button button = new Button("Start / Stop");
        button.setPrefSize(150, 85);
        button.setOnAction(event -> {
            group3D.toggleAnimation();
        });
        return button;
    }

    private HBox addSpeedOptions() {
        Label xLabel = new Label("X Speed:");
        xLabel.setTextFill(Color.RED);
        TextField xTextField = new TextField("1");
        xTextField.setPrefWidth(40);
        Slider xSlider = prepareSlider(270.0);

        Label yLabel = new Label("Y Speed:");
        yLabel.setTextFill(Color.GREEN);
        TextField yTextField = new TextField("1");
        yTextField.setPrefWidth(40);
        Slider ySlider = prepareSlider(0.0);

        Label zLabel = new Label("Z Speed:");
        zLabel.setTextFill(Color.BLUE);
        TextField zTextField = new TextField("1");
        zTextField.setPrefWidth(40);
        Slider zSlider = prepareSlider(0.0);

        Button confirmButton = new Button("Confirm");
        confirmButton.setPrefSize(60, 72);
        confirmButton.setOnAction(event -> {
            double xSpeed;
            double ySpeed;
            double zSpeed;
            double rotX = xSlider.getValue();
            double rotY = ySlider.getValue();
            double rotZ = zSlider.getValue();

            try {
                xSpeed = Double.parseDouble(xTextField.getText());
            } catch (Exception e) {
                xSpeed = 1;
            } try {
                ySpeed = Double.parseDouble(yTextField.getText());
            } catch (Exception e) {
                ySpeed = 1;
            } try {
                zSpeed = Double.parseDouble(zTextField.getText());
            } catch (Exception e) {
                zSpeed = 1;
            }
            group3D.reset(xSpeed, rotX, ySpeed, rotY, zSpeed, rotZ);
        });

        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(xTextField, xSlider);
        vBox1.setSpacing(5);
        vBox1.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
        vBox1.setPadding(new Insets(5, 5, 0, 5));

        VBox vBox2 = new VBox();
        vBox2.getChildren().addAll(yTextField, ySlider);
        vBox2.setSpacing(5);
        vBox2.setStyle("-fx-background-color: rgba(0, 255, 0, 0.5);");
        vBox2.setPadding(new Insets(5, 5, 0, 5));

        VBox vBox3 = new VBox();
        vBox3.getChildren().addAll(zTextField, zSlider);
        vBox3.setSpacing(5);
        vBox3.setStyle("-fx-background-color: rgba(0, 0, 255, 0.3);");
        vBox3.setPadding(new Insets(5, 5, 0, 5));

        VBox labels = new VBox();
        Label freq = new Label("Frequency->");
        Label rot = new Label("Rotation->");
        labels.getChildren().addAll(freq, rot);
        labels.setPadding(new Insets(10,0,10,0));
        labels.setSpacing(20);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5,3,5,3));
        hBox.setStyle("-fx-background-color: rgba(216, 224, 230, 0.7);");
        hBox.setSpacing(10);
        hBox.getChildren().addAll(labels, vBox1, vBox2, vBox3, confirmButton);
        return hBox;
    }

    private Slider prepareSlider(double val) {
        Slider slider = new Slider(0.0, 360, val);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(60);
        slider.setMinorTickCount(10);
        return slider;
    }

    private HBox addToggleOptions() {
        Button axesButton = new Button("Toggle Axes");
        axesButton.setPrefSize(100, 72);
        axesButton.setOnAction(event -> {
            group3D.toggleAxes();
        });

        Button containerButton = new Button("Toggle Bounds");
        containerButton.setPrefSize(100, 72);
        containerButton.setOnAction(event -> {
            group3D.toggleContainer();
        });

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5,5,5,5));
        hBox.setStyle("-fx-background-color: rgba(216, 224, 230, 0.7);");
        hBox.setSpacing(5);
        hBox.getChildren().addAll(axesButton, containerButton);
        return hBox;
    }

}
