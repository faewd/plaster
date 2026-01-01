package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Color;
import dev.faew.plaster.DebugMaterial;
import dev.faew.plaster.Light;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec2;
import dev.faew.plaster.geom.Vec3;

import java.awt.image.BufferedImage;
import java.util.List;

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
                        new Vec2(0.5, 0),
                        new Vec2(1, 1),
                        new Vec2(0, 1),
                        new DebugMaterial(Color.WHITE)
                ),
                new Triangle(
                        position,
                        new Vec3(s, s, s),
                        new Vec3(-s, -s, s),
                        new Vec3(s, -s, -s),
                        new Vec2(0.5, 0),
                        new Vec2(1, 1),
                        new Vec2(0, 1),
                        new DebugMaterial(Color.RED)
                ),
                new Triangle(
                        position,
                        new Vec3(-s, s, -s),
                        new Vec3(s, -s, -s),
                        new Vec3(s, s, s),
                        new Vec2(0.5, 0),
                        new Vec2(1, 1),
                        new Vec2(0, 1),
                        new DebugMaterial(Color.GREEN)
                ),
                new Triangle(
                        position,
                        new Vec3(-s, s, -s),
                        new Vec3(s, -s, -s),
                        new Vec3(-s, -s, s),
                        new Vec2(0.5, 0),
                        new Vec2(1, 1),
                        new Vec2(0, 1),
                        new DebugMaterial(Color.BLUE)
                )
        };
    }

    @Override
    public void render(BufferedImage img, double[] zBuffer, Vec3 offset, Matrix44 cameraTransform, List<Light> lights) {
        for (var face : faces) {
            face.render(img, zBuffer, offset, cameraTransform, lights);
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
