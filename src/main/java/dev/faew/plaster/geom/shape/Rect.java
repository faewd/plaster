package dev.faew.plaster.geom.shape;

import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec2;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.material.Material;

public class Rect extends CompositeShape {

    public Rect(Matrix44 transform, double w, double h, Material material) {
        this(
                transform,
                new Vec3(-w / 2, -h / 2, 0),
                new Vec3(w / 2, -h / 2, 0),
                new Vec3(w / 2, h / 2, 0),
                new Vec3(-w / 2, h / 2, 0),
                material
        );
    }

    public Rect(Matrix44 transform, Vec3 A, Vec3 B, Vec3 C, Vec3 D, Material material) {
        super(transform, new Triangle[] {
                new Triangle(transform, A, B, D, new Vec2(0, 0), new Vec2(1, 0), new Vec2(0, 1), material),
                new Triangle(transform, B, C, D, new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), material)
        });
    }
}
