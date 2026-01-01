package dev.faew.plaster.geom;

public class Vec2 {
    public double x, y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(Vec2 o) {
        return new Vec2(
                x + o.x,
                y + o.y
        );
    }

    public Vec2 subtract(Vec2 o) {
        return new Vec2(
                x - o.x,
                y - o.y
        );
    }

    public Vec2 multiply(double d) {
        return new Vec2(
                x * d,
                y * d
        );
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vec2 normalize() {
        double len = length();
        if (len == 0) {
            return Vec2.zero();
        }
        return new Vec2(x / len, y / len);
    }

    public static Vec2 zero() {
        return new Vec2(0, 0);
    }

    @Override
    public String toString() {
        return String.format("Vec2(%.3f, %.3f)", x, y);
    }
}
