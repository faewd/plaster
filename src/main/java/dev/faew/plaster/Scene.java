package dev.faew.plaster;

import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.geom.shape.Shape;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scene {

    private Vec3 cameraPosition = Vec3.zero();
    private Matrix33 cameraRotation = Matrix33.identity();

    private final List<Shape> shapes = new ArrayList<>();
    private final List<Light> lights = new ArrayList<>();

    public void lookAt(Vec3 target, Vec3 from) {
        final var forward = from.subtract(target).normalize();
        final var right = new Vec3(0, 1, 0).cross(forward).normalize();
        final var up = forward.cross(right).normalize();

        setCameraPosition(new Vec3(
                -right.dot(from),
                -up.dot(from),
                -forward.dot(from)
        ));

        setCameraRotation(new Matrix33(
                right.x, right.y, right.z,
                up.x, up.y, up.z,
                forward.x, forward.y, forward.z
        ));
    }

    public void setCameraPosition(Vec3 cameraPosition) {
        this.cameraPosition = cameraPosition;
    }

    public void setCameraRotation(Matrix33 cameraRotation) {
        this.cameraRotation = cameraRotation;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public BufferedImage render(int width, int height) {
        final var offset = new Vec3(width / 2.0, height / 2.0, 0);
        final var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        double[] zBuffer = new double[img.getWidth() * img.getHeight()];
        Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);

        final var cameraTransform = Matrix44.fromPosRot(cameraPosition, cameraRotation);

        for (Shape s : shapes) {
            s.render(img, zBuffer, offset, cameraTransform, lights);
        }

        return img;
    }
}
