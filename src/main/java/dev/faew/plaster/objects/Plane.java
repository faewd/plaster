package dev.faew.plaster.objects;

import dev.faew.plaster.Frame;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.shape.Rect;
import dev.faew.plaster.lighting.Light;
import dev.faew.plaster.material.Material;

import java.util.List;

public class Plane extends GameObject {

    private final Rect rect;

    public Plane(Matrix44 transform, double width, double height, Material material) {
        super(transform);
        this.rect = new Rect(transform, width, height, material);
    }

    @Override
    public void render(Frame frame, Matrix44 cameraTransform, List<Light> lights) {
        rect.render(frame, cameraTransform, lights);
    }
}
