package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Material;
import dev.faew.plaster.geom.Vec2;
import dev.faew.plaster.geom.Vec3;

public class Rect extends CompositeShape {

    public Rect(Vec3 position, double w, double h, Material material) {
        this(
                position,
                new Vec3(-w / 2, -h / 2, 0),
                new Vec3(w / 2, -h / 2, 0),
                new Vec3(w / 2, h / 2, 0),
                new Vec3(-w / 2, h / 2, 0),
                material
        );
    }

    public Rect(Vec3 position, Vec3 A, Vec3 B, Vec3 C, Vec3 D, Material material) {
        super(position, new Triangle[] {
                new Triangle(position, A, B, D, new Vec2(0, 0), new Vec2(1, 0), new Vec2(0, 1), material),
                new Triangle(position, B, C, D, new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), material)
        });
    }
}
