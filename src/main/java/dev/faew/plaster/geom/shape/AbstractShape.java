package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Frame;
import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.lighting.Light;

import java.util.List;

public abstract class AbstractShape implements Shape {

    private final Matrix44 transform;

    public AbstractShape(Matrix44 transform) {
        this.transform = transform;
    }

    @Override
    public abstract void render(Frame frame, Matrix44 cameraTransform, List<Light> lights);

    @Override
    public void setPosition(Vec3 position) {
        this.transform.setPosition(position);
    }

    @Override
    public void setRotation(Matrix33 rotation) {
        this.transform.setRotation(rotation);
    }

    public Matrix44 getTransform() {
        return transform;
    }
}
