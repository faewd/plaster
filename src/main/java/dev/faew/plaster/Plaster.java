package dev.faew.plaster;

import dev.faew.plaster.util.Config;
import dev.faew.plaster.util.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Plaster {
    private final Viewport viewport;
    private final Window window;
    private Scene scene;

    public List<Consumer<Double>> tickHandlers = new ArrayList<>();

    public Plaster() {
        this.viewport = new Viewport();
        this.window = new Window(viewport);

        window.setVisible(true);
    }

    public void start() {

        final var timer = Timer.getInstance();

        while (true) {
            final var frameProgress = timer.update();

            if (frameProgress >= 1) {
                final var delta = frameProgress * Config.getInstance().getDeltaScale();

                scene.tick(delta);

                tickHandlers.forEach(handler -> handler.accept(delta));

                viewport.repaint();

                timer.tick();
            }

            timer.reset();
        }

    }

    public void onTick(Consumer<Double> handler) {
        tickHandlers.add(handler);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        viewport.setScene(scene);
    }
}
