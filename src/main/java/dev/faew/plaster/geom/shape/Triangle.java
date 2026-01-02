package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Frame;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec2;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.lighting.Light;
import dev.faew.plaster.material.Material;
import dev.faew.plaster.util.Color;
import dev.faew.plaster.util.Config;

import java.util.List;

public class Triangle extends AbstractShape {
    private final Vec3 a, b, c;
    private final Vec2 uvA, uvB, uvC;
    private final Material material;

    public Triangle(Matrix44 transform, Vec3 a, Vec3 b, Vec3 c, Vec2 uvA, Vec2 uvB, Vec2 uvC, Material material) {
        super(transform);
        this.a = a;
        this.b = b;
        this.c = c;
        this.uvA = uvA;
        this.uvB = uvB;
        this.uvC = uvC;
        this.material = material;
    }

    @Override
    public void render(Frame frame, Matrix44 cameraTransform, List<Light> lights) {
        final var config = Config.getInstance();

        final var worldTransform = getTransform();

        final var worldA = this.a.multiply(worldTransform);
        final var worldB = this.b.multiply(worldTransform);
        final var worldC = this.c.multiply(worldTransform);
        final var AB = worldB.subtract(worldA);
        final var AC = worldC.subtract(worldA);
        final var normal = AB.cross(AC).normalize();

        final var transform = cameraTransform.multiply(worldTransform);
        final var scale = config.getPixelScale();
        final var a = this.a.multiply(transform).multiply(1d / scale);
        final var b = this.b.multiply(transform).multiply(1d / scale);
        final var c = this.c.multiply(transform).multiply(1d / scale);

        final var fov = config.getFov();
        a.x = a.x / (-a.z) * fov;
        a.y = a.y / (-a.z) * fov;
        b.x = b.x / (-b.z) * fov;
        b.y = b.y / (-b.z) * fov;
        c.x = c.x / (-c.z) * fov;
        c.y = c.y / (-c.z) * fov;

        a.x += frame.offset().x;
        a.y += frame.offset().y;
        b.x += frame.offset().x;
        b.y += frame.offset().y;
        c.x += frame.offset().x;
        c.y += frame.offset().y;

        final var triangleArea = (a.y - c.y) * (b.x - c.x) + (b.y - c.y) * (c.x - a.x);
        if (triangleArea <= 0) return;

        final var minX = (int) Math.max(0, Math.ceil(Math.min(a.x, Math.min(b.x, c.x))));
        final var maxX = (int) Math.min(frame.img().getWidth() - 1, Math.floor(Math.max(a.x, Math.max(b.x, c.x))));
        final var minY = (int) Math.max(0, Math.ceil(Math.min(a.y, Math.min(b.y, c.y))));
        final var maxY = (int) Math.min(frame.img().getHeight() - 1, Math.floor(Math.max(a.y, Math.max(b.y, c.y))));

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                final double b1 = ((y - c.y) * (b.x - c.x) + (b.y - c.y) * (c.x - x)) / triangleArea;
                final double b2 = ((y - a.y) * (c.x - a.x) + (c.y - a.y) * (a.x - x)) / triangleArea;
                final double b3 = ((y - b.y) * (a.x - b.x) + (a.y - b.y) * (b.x - x)) / triangleArea;

                if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {

                    double depth = b1 * a.z + b2 * b.z + b3 * c.z;
                    if (depth > 0) continue;
                    int zIndex = y * frame.img().getWidth() + x;
                    if (frame.zBuffer()[zIndex] < depth) {

                        double w = 1.0 / (b1 / a.z + b2 / b.z + b3 / c.z);
                        double u = w * (b1 * uvA.x / a.z + b2 * uvB.x / b.z + b3 * uvC.x / c.z);
                        double v = w * (b1 * uvA.y / a.z + b2 * uvB.y / b.z + b3 * uvC.y / c.z);

                        final var pos = new Vec3(
                                b1 * worldA.x + b2 * worldB.x + b3 * worldC.x,
                                b1 * worldA.y + b2 * worldB.y + b3 * worldC.y,
                                b1 * worldA.z + b2 * worldB.z + b3 * worldC.z
                        );

                        double lightLevel = config.getGlobalIllumination();

                        Color col;

                        if (material.isEmissive(u, v, 1)) {
                            col = material.getColor(u, v, 1);
                        } else {
                            for (var light : lights) {
                                final var ray = pos.subtract(light.transform().getPosition());
                                final var dist = ray.length();

                                final var cosTheta = ray.normalize().dot(normal);
                                final var r = light.radius();
                                if (cosTheta <= 0 || dist > r) continue;
                                final var rd = (dist / r);
                                final var lightContribution = (1 - (rd * rd)) * light.intensity() * cosTheta;
                                lightLevel = Math.min(1, lightLevel + lightContribution);
                            }

                            final var BANDS = 4;
                            final var band = 1 - Math.floor(lightLevel * BANDS) / (double)BANDS;
                            final var nextBand = 1 - Math.floor(lightLevel * BANDS + 1) / (double)BANDS;
                            final var relativeLight = lightLevel * BANDS % 1;
                            final var shadowOpacity = lightLevel >= 1 ? 0
                                    : lightLevel <= 0 ? 1
                                    : dither(x, y, relativeLight) ? nextBand
                                    : band;

                            col = Color.composite(
                                    material.getColor(u, v, 1),
                                    Color.BLACK.withOpacity(shadowOpacity)
                            );
                        }

                        frame.img().setRGB(x, y, col.value());
                        frame.zBuffer()[zIndex] = depth;
                    }
                }
            }
        }
    }

    private static final int DITHER_LEVELS = 16;
    private static final int DITHER_SIZE = 4;
    private static final byte[] DITHER_PATTERN = new byte[] {
            1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };

    private boolean dither(int x, int y, double d) {
        final var ditherLevel = (int)((1 - d) * (DITHER_LEVELS + 1)) - 1;
        if (ditherLevel == -1) return true;
        final var sampleX = (ditherLevel * DITHER_SIZE) + x % DITHER_SIZE;
        final var sampleY = y % DITHER_SIZE;
        return DITHER_PATTERN[sampleX + sampleY * DITHER_SIZE * DITHER_LEVELS] == 1;
    }
}
