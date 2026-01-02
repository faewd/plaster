package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Frame;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec2;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.lighting.Light;
import dev.faew.plaster.material.DebugMaterial;
import dev.faew.plaster.util.Color;

import java.util.List;

public class Tet extends AbstractShape {

    private final Triangle[] faces;

    public Tet(Matrix44 transform, double s) {
        super(transform);
        this.faces = new Triangle[] {
                new Triangle(
                        transform,
                        new Vec3(s, s, s),
                        new Vec3(-s, -s, s),
                        new Vec3(-s, s, -s),
                        new Vec2(0.5, 0),
                        new Vec2(1, 1),
                        new Vec2(0, 1),
                        new DebugMaterial(Color.WHITE)
                ),
                new Triangle(
                        transform,
                        new Vec3(s, s, s),
                        new Vec3(-s, -s, s),
                        new Vec3(s, -s, -s),
                        new Vec2(0.5, 0),
                        new Vec2(1, 1),
                        new Vec2(0, 1),
                        new DebugMaterial(Color.RED)
                ),
                new Triangle(
                        transform,
                        new Vec3(-s, s, -s),
                        new Vec3(s, -s, -s),
                        new Vec3(s, s, s),
                        new Vec2(0.5, 0),
                        new Vec2(1, 1),
                        new Vec2(0, 1),
                        new DebugMaterial(Color.GREEN)
                ),
                new Triangle(
                        transform,
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
    public void render(Frame frame, Matrix44 cameraTransform, List<Light> lights) {
        for (var face : faces) {
            face.render(frame, cameraTransform, lights);
        }
    }
}
