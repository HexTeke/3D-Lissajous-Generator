import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static final double STAGE_WIDTH = 1024.0;
    public static final double STAGE_HEIGHT = 768.0;
    private static final boolean Z_BUFFER = true;

    private Stage stage;
    private Group root;
    private Camera camera;
    private Scene scene;

    private Group3D group3D;
    private GUI gui;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setWidth(STAGE_WIDTH);
        stage.setHeight(STAGE_HEIGHT);
//        stage.setMinWidth(STAGE_WIDTH);
//        stage.setMinHeight(STAGE_HEIGHT);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("res/icon.png")));

        root = new Group();
        camera = new PerspectiveCamera();

        scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT, Z_BUFFER);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        group3D = new Group3D(scene, stage);
        gui = new GUI(stage);
        gui.setGroup3D(group3D);

        root.getChildren().add(gui);
        root.getChildren().add(group3D);

        primaryStage.setTitle("Pretzel");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
