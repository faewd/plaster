package dev.faew.plaster.geom;

public class Vec3 {
    public double x, y, z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 add(Vec3 o) {
        return new Vec3(
                x + o.x,
                y + o.y,
                z + o.z
        );
    }

    public Vec3 subtract(Vec3 o) {
        return new Vec3(
                x - o.x,
                y - o.y,
                z - o.z
        );
    }

    public Vec3 multiply(double d) {
        return new Vec3(
                x * d,
                y * d,
                z * d
        );
    }

    public Vec3 multiply(Matrix33 t) {
        return new Vec3(
                t.a * x + t.b * y + t.c * z,
                t.d * x + t.e * y + t.f * z,
                t.g * x + t.h * y + t.i * z
        );
    }

    public Vec3 multiply(Matrix44 t) {
        return new Vec3(
                t.m00 * x + t.m01 * y + t.m02 * z + t.m03,
                t.m10 * x + t.m11 * y + t.m12 * z + t.m13,
                t.m20 * x + t.m21 * y + t.m22 * z + t.m23
        );
    }

    public double dot(Vec3 o) {
        return x * o.x + y * o.y + z * o.z;
    }

    public Vec3 cross(Vec3 o) {
        return new Vec3(
                y * o.z - z * o.y,
                z * o.x - x * o.z,
                x * o.y - y * o.x
        );
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vec3 normalize() {
        double len = length();
        if (len == 0) {
            return Vec3.zero();
        }
        return new Vec3(x / len, y / len, z / len);
    }

    public static Vec3 zero() {
        return new Vec3(0, 0, 0);
    }

    @Override
    public String toString() {
        return String.format("Vec3(%.3f, %.3f, %.3f)", x, y, z);
    }
}
