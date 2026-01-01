package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Light;
import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Shape {
    void render(BufferedImage img, double[] zBuffer, Vec3 offset, Matrix44 cameraTransform, List<Light> lights);

    void setPosition(Vec3 position);
    void setRotation(Matrix33 rotation);
    void setTransform(Matrix44 transform);
}
