package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Light;
import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class AbstractShape implements Shape {

    private Vec3 position;
    private Matrix33 rotation;
    private Matrix44 transform;

    public AbstractShape(Vec3 position) {
        this(position, Matrix33.identity());
    }

    public AbstractShape(Vec3 position, Matrix33 rotation) {
        this.position = position;
        this.rotation = rotation;
        this.updateTransform();
    }

    @Override
    public abstract void render(BufferedImage img, double[] zBuffer, Vec3 offset, Matrix44 cameraTransform, List<Light> lights);

    private void updateTransform() {
        this.transform = Matrix44.fromPosRot(position, rotation);
//        System.out.println(transform);
    }

    @Override
    public void setPosition(Vec3 position) {
        this.position = position;
        this.updateTransform();
    }

    @Override
    public void setRotation(Matrix33 rotation) {
        this.rotation = rotation;
        this.updateTransform();
    }

    @Override
    public void setTransform(Matrix44 transform) {
        this.transform = transform;
        this.position = transform.getTranslation();
        this.rotation = transform.getRotation();
    }

    public Matrix44 getTransform() {
        return transform;
    }
}
