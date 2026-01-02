package dev.faew.plaster.geom.shape;

import dev.faew.plaster.Frame;
import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.lighting.Light;

import java.util.List;

public interface Shape {
    void render(Frame frame, Matrix44 cameraTransform, List<Light> lights);

    void setPosition(Vec3 position);
    void setRotation(Matrix33 rotation);
}
