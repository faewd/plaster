package dev.faew.plaster.material;

import dev.faew.plaster.util.Color;

public interface Material {
    Color getColor(double u, double v, double seed);

    default boolean isEmissive(double u, double v, double seed) {
        return false;
    }
}
