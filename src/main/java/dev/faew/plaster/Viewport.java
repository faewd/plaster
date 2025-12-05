package dev.faew.plaster;

import javax.swing.*;
import java.awt.*;

public class Viewport extends JPanel {

    private static final boolean DEBUG = true;

    private final int width, height;
    private Scene scene = null;

    private final int[] ftBuffer = new int[1000];
    private int ftPtr;

    public Viewport(int width, int height, int pixelScale) {
        super();
        this.setSize(width * pixelScale, height * pixelScale);

        this.width = width;
        this.height = height;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void paintComponent(Graphics g) {
        final var start = System.nanoTime();
        final var g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (scene != null) {
            final var img = scene.render(width, height).getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
            g2.drawImage(img, 0, 0, null);
        }

        if (DEBUG) {
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
                g2.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(width - i, barHeight, width - i, barHeight - ft);
            }

            ftPtr += 1;

            g2.setColor(Color.WHITE);
            g2.drawString(Plaster.FPS + "fps", 0, 10);
            g2.drawString(frameTime + "μs", 0, 20);
        }
    }

}
