package dev.faew.plaster;

import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.geom.shape.Box;
import dev.faew.plaster.geom.shape.Rect;

public class Plaster {

    // Configurable
    private static final double FOV_ANGLE = Math.toRadians(100);
    private static final int FRAME_CAP = 120;
    public static final double GLOBAL_ILLUMINATION = 0.2;

    // Computed / DO NOT CHANGE
    public static int SCALE = 3;
    public static final double FOV = Math.tan(FOV_ANGLE / 2) * 170;
    private static final int REFERENCE_FRAME_RATE = 120;
    private static final double DELTA_SCALE = (double)REFERENCE_FRAME_RATE / FRAME_CAP;
    private static final double OPTIMAL_FRAME_TIME = (1000000000d / FRAME_CAP);

    public static long FPS;
    private static long fps;
    private static long lastLoop = System.nanoTime();
    private static double frameProgress;
    private static long secondStart = System.nanoTime();

    public static void main(String[] args) {

        final var scene = new Scene();
        scene.lookAt(new Vec3(0, 0, 0), new Vec3(-900, -500, 0));

        final var light = new Light(new Vec3(0, -200, 0), 1000, 1);
        final var lightShape = new Box(new Vec3(0, -200, 0), 20, 40, 20, new DebugMaterial(Color.YELLOW));
        scene.addLight(light);
        scene.addShape(lightShape);

        final var floor = new Rect(new Vec3(0, 0, 0), 1000, 1000, new DebugMaterial(Color.WHITE));
        floor.setRotation(Matrix33.fromRollPitchYaw(Math.toRadians(-90), 0, 0));
        scene.addShape(floor);

        final var window = new Window();
        final var viewport = new Viewport(800 / SCALE, 800 / SCALE, SCALE);

        viewport.setScene(scene);
        window.init(viewport);

        double t = 0;
        while (true) {
            final var now = System.nanoTime();
            final var elapsed = now - lastLoop;
            frameProgress += elapsed / OPTIMAL_FRAME_TIME;
            lastLoop = now;

            if (frameProgress >= 1) {
                final var delta = frameProgress * DELTA_SCALE;
                t += 0.2 * delta;

                light.setPosition(new Vec3(0, Math.sin(t/20) * 100 - 200, 0));
                lightShape.setPosition(new Vec3(0, Math.sin(t/20) * 100 - 200, 0));

                viewport.repaint();

                frameProgress = 0;
                fps++;
            }

            final var timeElapsed = now - secondStart;
            if (timeElapsed >= 1000000000d) {
                FPS = fps;
                fps = 0;
                secondStart = now;
            }
        }

    }
}
