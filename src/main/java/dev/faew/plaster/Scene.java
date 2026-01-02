package dev.faew.plaster;

import dev.faew.plaster.lighting.Light;
import dev.faew.plaster.lighting.LightSource;
import dev.faew.plaster.objects.GameObject;

import java.util.*;

public class Scene {

    private final Camera camera = new Camera();

    private final List<GameObject> objects = new ArrayList<>();

    private final List<Light> lights = new ArrayList<>();
    private final Map<GameObject, Light> lightMap = new HashMap<>();

    public final List<GameObject> objectsToRemove = new ArrayList<>();

    public Camera getCamera() {
        return camera;
    }

    public void add(GameObject object) {
        objects.add(object);

        if (object instanceof LightSource ls) {
            final var light = new Light(object.getTransform(), ls.getRadius(), ls.getIntensity());
            lights.add(light);
            lightMap.put(object, light);
        }
    }

    public void remove(GameObject object) {
        objectsToRemove.add(object);
    }

    public void tick(double delta) {
        for (var object : objects) {
            if (objectsToRemove.contains(object)) continue;
            object.tick(delta);
        }

        for (var object : objectsToRemove) {
            objects.remove(object);
            if (lightMap.containsKey(object)) {
                final var light = lightMap.get(object);
                lights.remove(light);
            }
        }
        objectsToRemove.clear();
    }

    public void render(Frame frame) {
        Arrays.fill(frame.zBuffer(), Double.NEGATIVE_INFINITY);

        final var cameraTransform = camera.getTransform();

        for (var object : objects) {
            object.render(frame, cameraTransform, lights);
        }
    }
}
