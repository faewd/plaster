package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Color;
import dev.faew.plaster.DebugMaterial;
import dev.faew.plaster.Material;
import dev.faew.plaster.geom.Vec3;

public class Box extends CompositeShape {

    public Box(Vec3 position, double width, double height, double depth) {
        this(position, width, height, depth, null);
    }

    public Box(Vec3 position, double width, double height, double depth, Material material) {
        super(position, createFaces(position, width, height, depth, material));
    }

    private static Rect[] createFaces(Vec3 position, double width, double height, double depth, Material material) {
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
                new Rect(position, A, B, C, D, material != null ? material : new DebugMaterial(Color.RED)),
                new Rect(position, A, E, F, B, material != null ? material : new DebugMaterial(Color.GREEN)),
                new Rect(position, D, H, E, A, material != null ? material : new DebugMaterial(Color.BLUE)),
                new Rect(position, B, F, G, C, material != null ? material : new DebugMaterial(Color.YELLOW)),
                new Rect(position, C, G, H, D, material != null ? material : new DebugMaterial(Color.CYAN)),
                new Rect(position, E, H, G, F, material != null ? material : new DebugMaterial(Color.MAGENTA)),
        };
    }
}
