import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Tracer extends Sphere {

    private double xPos, yPos, zPos;

    public Tracer(String axis) {
        this.setRadius(5);
        setAxisProperties(axis);
    }

    public void reset() {
        this.setRotate(0);
    }

    private void setAxisProperties(String axis) {
        if((axis).equals("x")) {
            xPos = 0;
            yPos = 0;
            zPos = 100;
            this.getTransforms().add(new Translate(0,-50,0));
            this.setRotationAxis(Rotate.Z_AXIS);
        } else if((axis).equals("y")) {
            xPos = -100;
            yPos = 0;
            zPos = 0;
            this.getTransforms().add(new Translate(0,-50,0));
            this.setRotationAxis(Rotate.X_AXIS);
        } else {
            xPos = 0;
            yPos = -100;
            zPos = 0;
            this.getTransforms().add(new Translate(0,0,-50));
            this.setRotationAxis(Rotate.Y_AXIS);
        }
        this.setTranslateX(xPos);
        this.setTranslateY(yPos);
        this.setTranslateZ(zPos);
    }

}
