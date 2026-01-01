package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Light;
import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class CompositeShape extends AbstractShape {

    private final int n;
    private final Shape[] children;

    public CompositeShape(Vec3 position, Shape[] children) {
        super(position);
        this.children = children;
        this.n = children.length;
    }

    @Override
    public void render(BufferedImage img, double[] zBuffer, Vec3 offset, Matrix44 cameraTransform, List<Light> lights) {
        for (int i = 0; i < n; i++) children[i].render(img, zBuffer, offset, cameraTransform, lights);
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

    @Override
    public void setTransform(Matrix44 transform) {
        super.setTransform(transform);
        for (int i = 0; i < n; i++) children[i].setTransform(transform);
    }
}
