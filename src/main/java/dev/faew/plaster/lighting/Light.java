package dev.faew.plaster.lighting;

import dev.faew.plaster.geom.Matrix44;

public record Light(Matrix44 transform, double radius, double intensity) {}
