package dev.faew.plaster.objects;

import dev.faew.plaster.Frame;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.lighting.Light;

import java.util.List;

public abstract class GameObject {

    private final Matrix44 transform;

    public GameObject(Matrix44 transform) {
        this.transform = transform;
    }

    public Matrix44 getTransform() {
        return transform;
    }

    public void tick(double delta) {}
    public void render(Frame frame, Matrix44 cameraTransform, List<Light> lights) {}
}
