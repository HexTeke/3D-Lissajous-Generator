import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;

public class CirclePane extends Pane {

    public CirclePane(String axis, Color color) {
        this.setPrefSize(100, 100);
        this.getChildren().add(new Text(-50, 0, axis));
        this.getChildren().add(createEllipsePath(color));
    }

    /**
     * Creates the path (colored circle) that the tracers follow. Modified from
     * https://stackoverflow.com/a/50137465/9105357
     */
    private Path createEllipsePath(Color color) {
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
        path.setStroke(color);
        path.setStrokeWidth(4.0);

        return path;
    }

}
