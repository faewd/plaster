package dev.faew.plaster.geom;

public class Matrix33 {
    double  a, b, c,
            d, e, f,
            g, h, i;

    public Matrix33(double a, double b, double c,
                    double d, double e, double f,
                    double g, double h, double i) {
        this.a = a; this.b = b; this.c = c;
        this.d = d; this.e = e; this.f = f;
        this.g = g; this.h = h; this.i = i;
    }

    public Matrix33 multiply(Matrix33 o) {
        return new Matrix33(
                a * o.a + b * o.d + c * o.g, a * o.b + b * o.e + c * o.h, a * o.c + b * o.f + c * o.i,
                d * o.a + e * o.d + f * o.g, d * o.b + e * o.e + f * o.h, d * o.c + e * o.f + f * o.i,
                g * o.a + h * o.d + i * o.g, g * o.b + h * o.e + i * o.h, g * o.c + h * o.f + i * o.i
        );
    }

    public static Matrix33 identity() {
        return new Matrix33(
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        );
    }

    public static Matrix33 fromRollPitchYaw(double roll, double pitch, double yaw) {
//        // Roll around X-axis
//        final var rollTransform = new Matrix33(
//                1, 0, 0,
//                0, Math.cos(roll), -Math.sin(roll),
//                0, Math.sin(roll), Math.cos(roll)
//        );
//
//        // Pitch around Y-axis
//        final var pitchTransform = new Matrix33(
//                Math.cos(pitch), 0, Math.sin(pitch),
//                0, 1, 0,
//                -Math.sin(pitch), 0, Math.cos(pitch)
//        );
//
//        // Yaw around Z-axis
//        final var yawTransform = new Matrix33(
//                Math.cos(yaw), -Math.sin(yaw), 0,
//                Math.sin(yaw), Math.cos(yaw), 0,
//                0, 0, 1
//        );
//
        final var sinA = Math.sin(yaw);
        final var cosA = Math.cos(yaw);
        final var sinB = Math.sin(pitch);
        final var cosB = Math.cos(pitch);
        final var sinC = Math.sin(roll);
        final var cosC = Math.cos(roll);

        return new Matrix33(
                cosA * cosB, cosA * sinB * sinC - sinA * cosC, cosA * sinB * cosC + sinA * sinC,
                sinA * cosB, sinA * sinB * sinC + cosA * cosC, sinA * sinB * cosC - cosA * sinC,
                -sinB, cosB * sinC, cosB * cosC
        );

//        return yawTransform
//                .multiply(pitchTransform)
//                .multiply(rollTransform);
    }
}
