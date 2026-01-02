package dev.faew.plaster.util;

public class Timer {

    private static Timer instance = null;

    public static Timer getInstance() {
        if (instance == null) instance = new Timer();
        return instance;
    }

    private long FPS;
    private long fps;
    private long lastLoop = System.nanoTime();
    private double frameProgress;
    private long secondStart = System.nanoTime();
    private long now;

    public long getFPS() {
        return FPS;
    }

    public double update() {
        final var optimalFrameTime = Config.getInstance().getOptimalFrameTime();
        now = System.nanoTime();
        final var elapsed = now - lastLoop;
        frameProgress += elapsed / optimalFrameTime;
        lastLoop = now;
        return frameProgress;
    }

    public void tick() {
        frameProgress = 0;
        fps++;
    }

    public void reset() {
        final var timeElapsed = now - secondStart;
        if (timeElapsed >= 1000000000d) {
            FPS = fps;
            fps = 0;
            secondStart = now;
        }
    }
}
