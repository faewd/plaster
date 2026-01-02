package dev.faew.plaster.geom;

public class Matrix44 {
    public double m00, m01, m02, m03,
                  m10, m11, m12, m13,
                  m20, m21, m22, m23,
                  m30, m31, m32, m33;

    public Matrix44(double m00, double m01, double m02, double m03,
                    double m10, double m11, double m12, double m13,
                    double m20, double m21, double m22, double m23,
                    double m30, double m31, double m32, double m33) {
        this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
        this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
        this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
        this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
    }

    public Matrix44 multiply(Matrix44 o) {
        return new Matrix44(
                m00 * o.m00 + m01 * o.m10 + m02 * o.m20 + m03 * o.m30,
                m00 * o.m01 + m01 * o.m11 + m02 * o.m21 + m03 * o.m31,
                m00 * o.m02 + m01 * o.m12 + m02 * o.m22 + m03 * o.m32,
                m00 * o.m03 + m01 * o.m13 + m02 * o.m23 + m03 * o.m33,

                m10 * o.m00 + m11 * o.m10 + m12 * o.m20 + m13 * o.m30,
                m10 * o.m01 + m11 * o.m11 + m12 * o.m21 + m13 * o.m31,
                m10 * o.m02 + m11 * o.m12 + m12 * o.m22 + m13 * o.m32,
                m10 * o.m03 + m11 * o.m13 + m12 * o.m23 + m13 * o.m33,

                m20 * o.m00 + m21 * o.m10 + m22 * o.m20 + m23 * o.m30,
                m20 * o.m01 + m21 * o.m11 + m22 * o.m21 + m23 * o.m31,
                m20 * o.m02 + m21 * o.m12 + m22 * o.m22 + m23 * o.m32,
                m20 * o.m03 + m21 * o.m13 + m22 * o.m23 + m23 * o.m33,

                m30 * o.m00 + m31 * o.m10 + m32 * o.m20 + m33 * o.m30,
                m30 * o.m01 + m31 * o.m11 + m32 * o.m21 + m33 * o.m31,
                m30 * o.m02 + m31 * o.m12 + m32 * o.m22 + m33 * o.m32,
                m30 * o.m03 + m31 * o.m13 + m32 * o.m23 + m33 * o.m33
        );
    }

    public Matrix33 getRotation() {
        return new Matrix33(
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
        );
    }

    public void setRotation(Matrix33 rotation) {
        this.m00 = rotation.a; this.m01 = rotation.b; this.m02 = rotation.c;
        this.m10 = rotation.d; this.m11 = rotation.e; this.m12 = rotation.f;
        this.m20 = rotation.g; this.m21 = rotation.h; this.m22 = rotation.i;
    }

    public Vec3 getPosition() {
        return new Vec3(m03, m13, m23);
    }

    public void setPosition(Vec3 position) {
        this.m03 = position.x;
        this.m13 = position.y;
        this.m23 = position.z;
    }

    public void set(Matrix44 o) {
        this.m00 = o.m00; this.m01 = o.m01; this.m02 = o.m02; this.m03 = o.m03;
        this.m10 = o.m10; this.m11 = o.m11; this.m12 = o.m12; this.m13 = o.m13;
        this.m20 = o.m20; this.m21 = o.m21; this.m22 = o.m22; this.m23 = o.m23;
        this.m30 = o.m30; this.m31 = o.m31; this.m32 = o.m32; this.m33 = o.m33;
    }

    public static Matrix44 identity() {
        return new Matrix44(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
    }

    public static Matrix44 from(Vec3 position) {
        return new Matrix44(
                1, 0, 0, position.x,
                0, 1, 0, position.y,
                0, 0, 1, position.z,
                0, 0, 0, 1
        );
    }

    public static Matrix44 from(Matrix33 rot) {
        return new Matrix44(
                rot.a, rot.b, rot.c, 0,
                rot.d, rot.e, rot.f, 0,
                rot.g, rot.h, rot.i, 0,
                0, 0, 0, 1
        );
    }

    public static Matrix44 from(Vec3 pos, Matrix33 rot) {
        return new Matrix44(
                rot.a, rot.b, rot.c, pos.x,
                rot.d, rot.e, rot.f, pos.y,
                rot.g, rot.h, rot.i, pos.z,
                0, 0, 0, 1
        );
    }

    public static Matrix44 fromRollPitchYaw(double roll, double pitch, double yaw) {
        // Roll around X-axis
        final var rollTransform = new Matrix44(
                1, 0, 0, 0,
                0, Math.cos(roll), -Math.sin(roll), 0,
                0, Math.sin(roll), Math.cos(roll), 0,
                0, 0, 0, 1
        );

        // Pitch around Y-axis
        final var pitchTransform = new Matrix44(
                Math.cos(pitch), 0, Math.sin(pitch), 0,
                0, 1, 0, 0,
                -Math.sin(pitch), 0, Math.cos(pitch), 0,
                0, 0, 0, 1
        );

        // Yaw around Z-axis
        final var yawTransform = new Matrix44(
                Math.cos(yaw), -Math.sin(yaw), 0, 0,
                Math.sin(yaw), Math.cos(yaw), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );

        return yawTransform
                .multiply(pitchTransform)
                .multiply(rollTransform);
    }

    public static Matrix44 translation(Vec3 pos) {
        return new Matrix44(
                1, 0, 0, pos.x,
                0, 1, 0, pos.y,
                0, 0, 1, pos.z,
                0, 0, 0, 1
        );
    }

    @Override
    public String toString() {
        return String.format(
                "Matrix44[\n  [%.3f, %.3f, %.3f, %.3f]\n  [%.3f, %.3f, %.3f, %.3f]\n  [%.3f, %.3f, %.3f, %.3f]\n  [%.3f, %.3f, %.3f, %.3f]\n]",
                m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33
        );
    }
}
