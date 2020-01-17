import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        hBox.setPadding(new Insets(5, 12, 7, 12));
        hBox.setSpacing(19);
        hBox.prefWidthProperty().bind(stage.widthProperty());
        hBox.setStyle("-fx-background-color: rgba(0, 50, 200, 0.7);");

        hBox.getChildren().addAll(
                addPlayButton(),
                addSpeedOptions(),
                addToggleOptions(),
                addExportButton()
        );

        return hBox;
    }

    private Button addPlayButton() {
        Button button = new Button("Start / Stop");
        button.setPrefSize(150, 50);
        button.setOnAction(event -> {
            group3D.toggleAnimation();
        });

        return button;
    }

    private Button addExportButton() {
        Button button = new Button("Export");
        button.setPrefSize(150, 50);
        button.setDisable(true);
        button.setOnAction(event -> {

        });
        return button;
    }

    private HBox addToggleOptions() {
        Button axesButton = new Button("Toggle Axes");
        axesButton.setPrefSize(100, 40);
        axesButton.setTranslateY(5);
        axesButton.setOnAction(event -> {
            group3D.toggleAxes();
        });

        Button containerButton = new Button("Toggle Bounds");
        containerButton.setPrefSize(100, 40);
        containerButton.setTranslateY(5);
        containerButton.setOnAction(event -> {
            group3D.toggleContainer();
        });

        Button view2DButton = new Button("Toggle 2D View");
        view2DButton.setPrefSize(100, 40);
        view2DButton.setTranslateY(5);
        view2DButton.setDisable(true);
        view2DButton.setOnAction(event -> {

        });

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0,5,0,5));
        hBox.setStyle("-fx-background-color: rgba(216, 224, 230, 0.7);");
        hBox.setSpacing(10);
        hBox.getChildren().addAll(axesButton, containerButton, view2DButton);

        return hBox;
    }

    private HBox addSpeedOptions() {
        Label xLabel = new Label("X Speed:");
        xLabel.setTextFill(Color.RED);
        TextField xTextField = new TextField("1");
        xTextField.setPrefWidth(40);

        Label yLabel = new Label("Y Speed:");
        yLabel.setTextFill(Color.GREEN);
        TextField yTextField = new TextField("1");
        yTextField.setPrefWidth(40);

        Label zLabel = new Label("Z Speed:");
        zLabel.setTextFill(Color.BLUE);
        TextField zTextField = new TextField("1");
        zTextField.setPrefWidth(40);

        Button resetButton = new Button("Reset");
        resetButton.setPrefSize(60, 40);
        resetButton.setTranslateY(5);
        resetButton.setOnAction(event -> {
            xTextField.setText("1");
            yTextField.setText("1");
            zTextField.setText("1");
            group3D.reset(1, 1, 1);
        });

        Button confirmButton = new Button("Confirm");
        confirmButton.setPrefSize(60, 40);
        confirmButton.setTranslateY(5);
        confirmButton.setOnAction(event -> {
            double xSpeed;
            double ySpeed;
            double zSpeed;

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
            group3D.reset(xSpeed, ySpeed, zSpeed);
        });

        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(xLabel, xTextField);
        vBox1.setSpacing(5);

        VBox vBox2 = new VBox();
        vBox2.getChildren().addAll(yLabel, yTextField);
        vBox2.setSpacing(5);

        VBox vBox3 = new VBox();
        vBox3.getChildren().addAll(zLabel, zTextField);
        vBox3.setSpacing(5);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0,5,0,5));
        hBox.setStyle("-fx-background-color: rgba(216, 224, 230, 0.7);");
        hBox.setSpacing(10);
        hBox.getChildren().addAll(resetButton, vBox1, vBox2, vBox3, confirmButton);

        return hBox;
    }

}
