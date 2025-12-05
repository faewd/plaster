package dev.faew.plaster.geom.shape;

import dev.faew.plaster.geom.Vec3;

import java.awt.*;

public class Box extends CompositeShape {

    public Box(Vec3 position, double width, double height, double depth) {
        super(position, createFaces(position, width, height, depth));
    }

    private static Rect[] createFaces(Vec3 position, double width, double height, double depth) {
        final var wr = width / 2;
        final var hr = height / 2;
        final var dr = depth / 2;

        final var A = new Vec3(-wr, -hr, -dr);
        final var B = new Vec3(wr, -hr, -dr);
        final var C = new Vec3(wr, hr, -dr);
        final var D = new Vec3(-wr, hr, -dr);
        final var E = A.add(new Vec3(0, 0, depth));
        final var F = B.add(new Vec3(0, 0, depth));
        final var G = C.add(new Vec3(0, 0, depth));
        final var H = D.add(new Vec3(0, 0, depth));
        return new Rect[] {
                new Rect(position, A, B, C, D, Color.RED),
                new Rect(position, A, B, F, E, Color.GREEN),
                new Rect(position, D, A, E, H, Color.BLUE),
                new Rect(position, B, F, G, C, Color.YELLOW),
                new Rect(position, C, D, H, G, Color.CYAN),
                new Rect(position, E, H, G, F, Color.MAGENTA),
        };
    }
}
