package dev.faew.plaster;

import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;

public class Camera {
    private final Matrix44 transform = Matrix44.identity();

    public void setPosition(Vec3 position) {
        this.transform.setPosition(position);
    }

    public void setRotation(Matrix33 rotation) {
        this.transform.setRotation(rotation);
    }

    public void setTransform(Matrix44 transform) {
        this.transform.set(transform);
    }

    public Matrix44 getTransform() {
        return transform;
    }

    public void lookAt(Vec3 target) {
        final var from = transform.getPosition();
        final var forward = from.subtract(target).normalize();
        final var right = new Vec3(0, 1, 0).cross(forward).normalize();
        final var up = forward.cross(right).normalize();

        transform.setPosition(new Vec3(
                -right.dot(from),
                -up.dot(from),
                -forward.dot(from)
        ));

        transform.setRotation(new Matrix33(
                right.x, right.y, right.z,
                up.x, up.y, up.z,
                forward.x, forward.y, forward.z
        ));
    }
}
