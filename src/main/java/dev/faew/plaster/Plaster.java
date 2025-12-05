package dev.faew.plaster;

import dev.faew.plaster.geom.Matrix33;
import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.geom.shape.Box;
import dev.faew.plaster.geom.shape.Rect;

import java.awt.*;

public class Plaster {

    // Configurable
    private static final double FOV_ANGLE = Math.toRadians(100);
    private static final int FRAME_CAP = 120;

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

//        final var rot = Math.toRadians(45);
//        final var box1 = new Box(new Vec3(0, 0, 0), 100, 100, 100);
//        box1.setTransform(Matrix44.fromPosRot(new Vec3(-200, 0, 0), Matrix33.fromRollPitchYaw(rot, rot, rot)));
//        System.out.println(box1.getTransform());
//
//        final var box2 = new Box(new Vec3(200, 0, 0), 100, 100, 100);
//        box2.setRotation(Matrix33.fromRollPitchYaw(rot, rot, rot));
//        System.out.println(box2.getTransform());

        final var scene = new Scene();

        final var box1 = new Box(new Vec3(-250, 0, 0), 100, 100, 100);
        scene.addShape(box1);

        final var box2 = new Box(new Vec3(0, 0, 0), 100, 100, 100);
        scene.addShape(box2);

        final var box3 = new Box(new Vec3(250, 0, 0), 100, 100, 100);
        scene.addShape(box3);

        final var floor = new Rect(new Vec3(0, 399, 0), 1000, 1000, Color.DARK_GRAY);
        floor.setRotation(Matrix33.fromRollPitchYaw(Math.toRadians(90), 0, 0));
        scene.addShape(floor);

//        final var leftWall = new Rect(new Vec3(-400, 150, 0), 800, 500, Color.WHITE);
//        leftWall.setRotation(Matrix33.fromRollPitchYaw(0, Math.toRadians(90), 0));
//        scene.addShape(leftWall);
//
//        final var rightWall = new Rect(new Vec3(400, 150, 0), 800, 500, Color.WHITE);
//        rightWall.setRotation(Matrix33.fromRollPitchYaw(0, Math.toRadians(270), 0));
//        scene.addShape(rightWall);
//
//        final var backWall = new Rect(new Vec3(0, 150, -400), 800, 500, Color.WHITE);
//        backWall.setRotation(Matrix33.fromRollPitchYaw(0, 0, 0));
//        scene.addShape(backWall);


//        scene.lookAt(new Vec3(0, 0, 0), new Vec3(0, 0, 1500));
//        scene.setCameraPosition(new Vec3(0, 0, -1000));

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
                t += 0.5 * delta;
                final var r = Math.toRadians(t);
                box1.setRotation(Matrix33.fromRollPitchYaw(r, 0, 0));
                box2.setRotation(Matrix33.fromRollPitchYaw(0, -2 * r, 0));
                box3.setRotation(Matrix33.fromRollPitchYaw(0, 0, r));

                box1.setPosition(new Vec3(-250, Math.sin(t/20) * 100, 0));
                box2.setPosition(new Vec3(0, Math.sin(t/20 + 2 * Math.PI / 3) * 100, 0));
                box3.setPosition(new Vec3(250, Math.sin(t/20 + 4 * Math.PI / 3) * 100, 0));

                scene.lookAt(new Vec3(0, 400, -400), new Vec3(0, -400, 1000));

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
