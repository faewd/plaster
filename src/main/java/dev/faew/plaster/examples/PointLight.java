package dev.faew.plaster.examples;

import dev.faew.plaster.Plaster;
import dev.faew.plaster.Scene;
import dev.faew.plaster.geom.Matrix44;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.material.DebugMaterial;
import dev.faew.plaster.objects.Bulb;
import dev.faew.plaster.objects.Plane;
import dev.faew.plaster.util.Color;

import java.util.concurrent.atomic.AtomicReference;

public class PointLight {
    public static void main(String[] args) {
        final var game = new Plaster();

        final var scene = new Scene();

        final var floor = new Plane(Matrix44.fromRollPitchYaw(Math.toRadians(-90), 0, 0), 1000, 1000, new DebugMaterial(Color.WHITE));
        scene.add(floor);

        final var light = new Bulb(Matrix44.from(new Vec3(0, -200, 0)));
        scene.add(light);

        final var camera = scene.getCamera();
        camera.setPosition(new Vec3(-900, -500, 0));
        camera.lookAt(new Vec3(0, 0, 0));

        game.setScene(scene);

        var t = new AtomicReference<>((double) 0);
        game.onTick(delta -> {
            final var time = t.updateAndGet(v -> v + delta * 0.5);
            light.getTransform().setPosition(new Vec3(0, Math.sin(time/40) * 100 - 200, 0));
            camera.setPosition(new Vec3(Math.sin(time/200) * 1000, -800, Math.cos(time/200) * 1000));
            camera.lookAt(new Vec3(0, 0, 0));
        });

        game.start();
    }
}
