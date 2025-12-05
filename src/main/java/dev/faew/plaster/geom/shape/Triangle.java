package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Plaster;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Triangle extends AbstractShape {
    private final Vec3 a, b, c;
    private final Color color;

    public Triangle(Vec3 position, Vec3 a, Vec3 b, Vec3 c, Color color) {
        super(position);
        this.a = a;
        this.b = b;
        this.c = c;
        this.color = color;
    }

    @Override
    public void render(BufferedImage img, double[] zBuffer, Vec3 offset, Matrix44 cameraTransform) {
        final var transform = cameraTransform.multiply(getTransform());
        final var a = this.a.multiply(transform).multiply(1d / Plaster.SCALE);
        final var b = this.b.multiply(transform).multiply(1d / Plaster.SCALE);
        final var c = this.c.multiply(transform).multiply(1d / Plaster.SCALE);

        a.x = a.x / (-a.z) * Plaster.FOV;
        a.y = a.y / (-a.z) * Plaster.FOV;
        b.x = b.x / (-b.z) * Plaster.FOV;
        b.y = b.y / (-b.z) * Plaster.FOV;
        c.x = c.x / (-c.z) * Plaster.FOV;
        c.y = c.y / (-c.z) * Plaster.FOV;

        a.x += offset.x;
        a.y += offset.y;
        b.x += offset.x;
        b.y += offset.y;
        c.x += offset.x;
        c.y += offset.y;

        final var minX = (int) Math.max(0, Math.ceil(Math.min(a.x, Math.min(b.x, c.x))));
        final var maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(a.x, Math.max(b.x, c.x))));
        final var minY = (int) Math.max(0, Math.ceil(Math.min(a.y, Math.min(b.y, c.y))));
        final var maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(a.y, Math.max(b.y, c.y))));

        double triangleArea = (a.y - c.y) * (b.x - c.x) + (b.y - c.y) * (c.x - a.x);

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                double b1 = ((y - c.y) * (b.x - c.x) + (b.y - c.y) * (c.x - x)) / triangleArea;
                double b2 = ((y - a.y) * (c.x - a.x) + (c.y - a.y) * (a.x - x)) / triangleArea;
                double b3 = ((y - b.y) * (a.x - b.x) + (a.y - b.y) * (b.x - x)) / triangleArea;
                if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                    double depth = b1 * a.z + b2 * b.z + b3 * c.z;
                    if (depth > 0) continue;
                    int zIndex = y * img.getWidth() + x;
                    if (zBuffer[zIndex] < depth) {
                        img.setRGB(x, y, color.getRGB());
                        zBuffer[zIndex] = depth;
                    }
                }
            }
        }
    }
}
