package dev.faew.plaster;

import dev.faew.plaster.geom.Vec3;

import java.awt.image.BufferedImage;

public record Frame(BufferedImage img, double[] zBuffer, Vec3 offset) {
}
