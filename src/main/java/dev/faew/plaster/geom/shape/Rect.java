package dev.faew.plaster.geom.shape;

import dev.faew.plaster.geom.Vec3;

import java.awt.*;

public class Rect extends CompositeShape {

    public Rect(Vec3 position, double w, double h, Color col) {
        this(
                position,
                new Vec3(-w / 2, -h / 2, 0),
                new Vec3(w / 2, -h / 2, 0),
                new Vec3(w / 2, h / 2, 0),
                new Vec3(-w / 2, h / 2, 0),
                col
        );
    }

    public Rect(Vec3 position, Vec3 A, Vec3 B, Vec3 C, Vec3 D, Color col) {
        super(position, new Triangle[] {
                new Triangle(position, A, B, D, col),
                new Triangle(position, B, C, D, col.darker())
        });
    }
}
