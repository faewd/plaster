package dev.faew.plaster;

import dev.faew.plaster.geom.Vec3;

public class Light {
    private Vec3 position;
    private final double radius, intensity;

    public Light(Vec3 position, double radius, double intensity) {
        this.position = position;
        this.radius = radius;
        this.intensity = intensity;
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public double getRadius() {
        return radius;
    }

    public double getIntensity() {
        return intensity;
    }
}
