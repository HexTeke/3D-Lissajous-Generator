import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Group3D extends Group {

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    private CirclePane xPane, yPane, zPane;
    private Tracer xTracer, yTracer, zTracer;
    private double xSpeed = 1, ySpeed = 1, zSpeed = 1;

    private Box container;

    private Pen3D pen3D;
    private ArrayList drawing;

    private AnimationTimer timer;

    // toggles
    public boolean isPlaying = false;
    private boolean axesShown = true;
    private boolean containerShown = true;

    public Group3D(Scene scene, Stage stage) {
        this.translateZProperty().set(-800);
        this.translateXProperty().bind(scene.widthProperty().divide(2));
        this.translateYProperty().bind(scene.heightProperty().divide(2).add(20));

        addPanes();
        addTracers();

        pen3D = new Pen3D();
        pen3D.setTranslateX(xTracer.localToParent(0,0,0).getX());
        pen3D.setTranslateY(yTracer.localToParent(0,0,0).getY());
        pen3D.setTranslateZ(zTracer.localToParent(0,0,0).getZ());
        this.getChildren().add(pen3D);

        drawing = new ArrayList();
        addContainer();

        prepareTimer();

        initMouseControl(scene, stage);

    }

    private void prepareTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Point3D origin = pen3D.localToParent(0,0,0);

                xTracer.setRotate(xTracer.getRotate() + xSpeed);
                yTracer.setRotate(yTracer.getRotate() - ySpeed);
                zTracer.setRotate(zTracer.getRotate() - zSpeed);

                double x = xTracer.localToParent(0,0,0).getX();
                double y = yTracer.localToParent(0,0,0).getY();
                double z = zTracer.localToParent(0,0,0).getZ();

                Point3D newPoint = new Point3D(x, y, z);

                pen3D.setTranslateX(x);
                pen3D.setTranslateY(y);
                pen3D.setTranslateZ(z);
                addLine(pen3D.createConnection(origin, newPoint));
            }
        };
    }

    /***** View Components *****/

    private void addPanes() {
        xPane = new CirclePane("x");
        yPane = new CirclePane("y");
        zPane = new CirclePane("z");
        this.getChildren().addAll(xPane, yPane, zPane);
    }

    private void addTracers() {
        xTracer = new Tracer("x");
        yTracer = new Tracer("y");
        zTracer = new Tracer("z");
        this.getChildren().addAll(xTracer, yTracer, zTracer);
    }

    public void addLine(Cylinder line) {
        this.getChildren().add(line);
        drawing.add(line);
    }

    private void addContainer() {
        container = new Box(100, 100, 100);
        container.setDrawMode(DrawMode.LINE);
        this.getChildren().add(container);
    }

    /***** GUI Interaction *****/

    public void toggleAnimation() {
        if(isPlaying) {
            timer.stop();
            isPlaying = false;
        } else {
            timer.start();
            isPlaying = true;
        }

    }

    public void toggleAxes() {
        if(axesShown) {
            this.getChildren().removeAll(xPane, yPane, zPane);
            this.getChildren().removeAll(xTracer, yTracer, zTracer);
            axesShown = false;
        }
        else {
            this.getChildren().addAll(xPane, yPane, zPane);
            this.getChildren().addAll(xTracer, yTracer, zTracer);
            axesShown = true;
        }

    }

    public void toggleContainer() {
        if(containerShown) {
            this.getChildren().remove(container);
            containerShown = false;
        } else {
            this.getChildren().add(container);
            containerShown = true;
        }
    }

    public void reset(double x, double y, double z) {
        // reset view
        this.translateZProperty().set(-800);
        angleX.set(0.0);
        angleY.set(0.0);

        // stop animation and clear drawing
        timer.stop();
        isPlaying = false;
        this.getChildren().removeAll(drawing);

        // reset tracers
        xTracer.reset();
        yTracer.reset();
        zTracer.reset();

        // reset pen
        pen3D.setTranslateX(xTracer.localToParent(0,0,0).getX());
        pen3D.setTranslateY(yTracer.localToParent(0,0,0).getY());
        pen3D.setTranslateZ(zTracer.localToParent(0,0,0).getZ());

        // set speeds
        xSpeed = x;
        ySpeed = y;
        zSpeed = z;
    }

    /***** *****/

    private void initMouseControl(Scene scene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        this.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            this.translateZProperty().set(this.getTranslateZ() - delta);
        });

    }

}
