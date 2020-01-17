import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class CirclePane extends Pane {

    private double xPos, yPos, zPos;

    public CirclePane(String axis) {
        this.setPrefSize(100, 100);
        this.getChildren().add(new Text(-50, 0, axis));
        this.getChildren().add(createEllipsePath(axis));
        setAxisProperties(axis);
    }

    private void setAxisProperties(String axis) {
        if((axis).equals("x")) {
            xPos = 50;
            yPos = 0;
            zPos = 100;
        } else if((axis).equals("y")) {
            xPos = -100;
            yPos = 0;
            zPos = 50;
            this.getTransforms().add(new Rotate(270, Rotate.Y_AXIS));
        } else {
            xPos = 50;
            yPos = -100;
            zPos = 0;
            this.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
        }
        this.setTranslateX(xPos);
        this.setTranslateY(yPos);
        this.setTranslateZ(zPos);
    }

    /**
     * Creates the path (colored circle) that the tracers follow. Modified from
     * https://stackoverflow.com/a/50137465/9105357
     */
    private Path createEllipsePath(String axis) {
        final double RADIUS = 50;
        final double CENTER_X = 0;
        final double CENTER_Y = 0;

        ArcTo arcTo = new ArcTo();
        arcTo.setX(CENTER_X - RADIUS + 1);
        arcTo.setY(CENTER_Y - RADIUS);
        arcTo.setLargeArcFlag(true);
        arcTo.setRadiusX(RADIUS);
        arcTo.setRadiusY(RADIUS);

        Path path = new Path();
        path.getElements().addAll(
                new MoveTo(CENTER_X - RADIUS, CENTER_Y - RADIUS),
                arcTo,
                new ClosePath()
        );

        if((axis).equals("x"))
            path.setStroke(Color.RED);
        else if((axis).equals("y"))
            path.setStroke(Color.GREEN);
        else
            path.setStroke(Color.BLUE);

        path.setStrokeWidth(4.0);

        return path;
    }

}
