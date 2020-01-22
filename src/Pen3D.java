import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Pen3D extends Sphere {

    public Pen3D() {
        this.setRadius(5);
    }

    /**
     * https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
     */
    public Cylinder createConnection(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(3, height);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(calcColor(target));
        line.setMaterial(material);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    private Color calcColor(Point3D target) {
        int x = (int)Math.ceil((target.getX() - -50) / (50 - -50) * (255 - 0) + 0);
        int y = (int)Math.ceil((target.getY() - -50) / (50 - -50) * (255 - 0) + 0);
        int z = (int)Math.ceil((target.getZ() - -50) / (50 - -50) * (255 - 0) + 0);

        return Color.rgb(x, y, z);
    }

}
