package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Frame;
import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.lighting.Light;

import java.util.List;

public abstract class CompositeShape extends AbstractShape {

    private final int n;
    private final Shape[] children;

    public CompositeShape(Matrix44 transform, Shape[] children) {
        super(transform);
        this.children = children;
        this.n = children.length;
    }

    @Override
    public void render(Frame frame, Matrix44 cameraTransform, List<Light> lights) {
        for (int i = 0; i < n; i++) children[i].render(frame, cameraTransform, lights);
    }

    @Override
    public void setPosition(Vec3 position) {
        super.setPosition(position);
        for (int i = 0; i < n; i++) children[i].setPosition(position);
    }

    @Override
    public void setRotation(Matrix33 rotation) {
        super.setRotation(rotation);
        for (int i = 0; i < n; i++) children[i].setRotation(rotation);
    }
}
