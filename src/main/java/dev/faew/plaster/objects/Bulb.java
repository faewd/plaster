package dev.faew.plaster.objects;

import dev.faew.plaster.Frame;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.shape.Box;
import dev.faew.plaster.lighting.Light;
import dev.faew.plaster.lighting.LightSource;
import dev.faew.plaster.material.Material;
import dev.faew.plaster.util.Color;

import java.util.List;

public class Bulb extends GameObject implements LightSource {

    private final Box geometry;

    public Bulb(Matrix44 transform) {
        super(transform);
        this.geometry = new Box(transform, 20, 40, 20, new Material() {
            @Override
            public Color getColor(double u, double v, double seed) {
                return Color.YELLOW;
            }

            @Override
            public boolean isEmissive(double u, double v, double seed) {
                return true;
            }
        });
    }

    @Override
    public void render(Frame frame, Matrix44 cameraTransform, List<Light> lights) {
        geometry.render(frame, cameraTransform, lights);
    }

    @Override
    public double getRadius() {
        return 1000;
    }

    @Override
    public double getIntensity() {
        return 1;
    }
}
