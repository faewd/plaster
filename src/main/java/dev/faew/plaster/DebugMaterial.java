package dev.faew.plaster;

public class DebugMaterial implements Material {

    private final Color color;

    public DebugMaterial(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(double u, double v, double seed) {
        return u + v > 1 ? color : color.darker();
    }
}
