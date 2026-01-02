package dev.faew.plaster.geom.shape;

import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.material.DebugMaterial;
import dev.faew.plaster.material.Material;
import dev.faew.plaster.util.Color;

public class Box extends CompositeShape {

    public Box(Matrix44 transform, double width, double height, double depth, Material material) {
        super(transform, createFaces(transform, width, height, depth, material));
    }

    private static Rect[] createFaces(Matrix44 transform, double width, double height, double depth, Material material) {
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
                new Rect(transform, A, B, C, D, material != null ? material : new DebugMaterial(Color.RED)),
                new Rect(transform, A, E, F, B, material != null ? material : new DebugMaterial(Color.GREEN)),
                new Rect(transform, D, H, E, A, material != null ? material : new DebugMaterial(Color.BLUE)),
                new Rect(transform, B, F, G, C, material != null ? material : new DebugMaterial(Color.YELLOW)),
                new Rect(transform, C, G, H, D, material != null ? material : new DebugMaterial(Color.CYAN)),
                new Rect(transform, E, H, G, F, material != null ? material : new DebugMaterial(Color.MAGENTA)),
        };
    }
}
