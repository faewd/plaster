package dev.faew.plaster.geom.shape;

import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tet extends AbstractShape {

    private final Triangle[] faces;

    public Tet(Vec3 position, double s) {
        super(position);
        this.faces = new Triangle[] {
                new Triangle(
                        position,
                        new Vec3(s, s, s),
                        new Vec3(-s, -s, s),
                        new Vec3(-s, s, -s),
                        Color.WHITE
                ),
                new Triangle(
                        position,
                        new Vec3(s, s, s),
                        new Vec3(-s, -s, s),
                        new Vec3(s, -s, -s),
                        Color.RED
                ),
                new Triangle(
                        position,
                        new Vec3(-s, s, -s),
                        new Vec3(s, -s, -s),
                        new Vec3(s, s, s),
                        Color.GREEN
                ),
                new Triangle(
                        position,
                        new Vec3(-s, s, -s),
                        new Vec3(s, -s, -s),
                        new Vec3(-s, -s, s),
                        Color.BLUE
                )
        };
    }

    @Override
    public void render(BufferedImage img, double[] zBuffer, Vec3 offset, Matrix44 cameraTransform) {
        for (var face : faces) {
            face.render(img, zBuffer, offset, cameraTransform);
        }
    }

    @Override
    public void setTransform(Matrix44 transform) {
        super.setTransform(transform);
        for (var face : faces) {
            face.setTransform(transform);
        }
    }
}
