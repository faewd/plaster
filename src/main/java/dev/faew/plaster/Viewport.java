package dev.faew.plaster;

import dev.faew.plaster.geom.Vec3;
import dev.faew.plaster.util.Config;
import dev.faew.plaster.util.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Viewport extends JPanel {

    private final int width, height;

    private Scene scene = null;

    private final int[] ftBuffer = new int[100];
    private int ftPtr;

    public Viewport() {
        super();
        final var config = Config.getInstance();

        final var width = config.getWidth();
        final var height = config.getHeight();
        this.setSize(width, height);

        final var scale = config.getPixelScale();
        this.width = width / scale;
        this.height = height / scale;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void paintComponent(Graphics g) {
        final var start = System.nanoTime();
        final var g2 = (Graphics2D) g;
        g2.setColor(java.awt.Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (scene != null) {
            final var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            double[] zBuffer = new double[img.getWidth() * img.getHeight()];
            final var offset = new Vec3(width / 2.0, height / 2.0, 0);
            final var frame = new Frame(img, zBuffer, offset);
            scene.render(frame);
            g2.drawImage(img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, null);
        }

        if (Config.getInstance().getDebug()) {
            final var frameTime = (int)((System.nanoTime() - start) / 1000);

            if (ftPtr >= ftBuffer.length) ftPtr = 0;
            ftBuffer[ftPtr] = frameTime;

            int max = 0;
            for (int j : ftBuffer) {
                if (j > max) max = j;
            }

            final var barHeight = 25;
            for (int i = 0; i < ftBuffer.length; i++) {
                int f = (i + ftPtr) % ftBuffer.length;
                int ft = (int)((ftBuffer[f] / (double)max) * barHeight);
                g2.setColor(java.awt.Color.BLUE);
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(i, barHeight, i, barHeight - ft);
            }

            ftPtr += 1;

            g2.setColor(java.awt.Color.WHITE);
            g2.drawString(Timer.getInstance().getFPS() + "fps", 0, 10);
            g2.drawString(frameTime + "μs", 0, 20);
        }
    }

}
