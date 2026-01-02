package dev.faew.plaster.util;

public class Config {

    private static Config instance = null;

    public static Config getInstance() {
        if (instance == null) instance = new Config();
        return instance;
    }

    private static final int REFERENCE_FRAME_RATE = 120;

    private boolean debug;

    private double fov;

    private double deltaScale, optimalFrameTime;

    private double globalIllumination;

    private int width, height;
    private int pixelScale;

    public Config() {
        this.setDebug(true);
        this.setFovAngle(Math.toRadians(100));
        this.setFrameCap(120);
        this.setGlobalIllumination(0.2);
        this.setSize(800, 800);
        this.setPixelScale(3);
    }

    public boolean getDebug() {
        return debug;
    }

    public double getFov() {
        return fov;
    }

    public double getDeltaScale() {
        return deltaScale;
    }

    public double getOptimalFrameTime() {
        return optimalFrameTime;
    }

    public double getGlobalIllumination() {
        return globalIllumination;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPixelScale() {
        return pixelScale;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setFovAngle(double fovAngle) {
        this.fov = Math.tan(fovAngle / 2) * 170;
    }

    public void setFrameCap(int frameCap) {
        this.deltaScale = (double)REFERENCE_FRAME_RATE / frameCap;
        this.optimalFrameTime = 1000000000d / frameCap;
    }


    private void setGlobalIllumination(double globalIllumination) {
        this.globalIllumination = globalIllumination;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void setPixelScale(int pixelScale) {
        this.pixelScale = pixelScale;
    }
}
