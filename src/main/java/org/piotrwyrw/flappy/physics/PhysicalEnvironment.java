package org.piotrwyrw.flappy.physics;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class PhysicalEnvironment {

    private ArrayList<GameObject> objects;
    private double gravitationalAcceleration;
    private boolean gravity;

    public PhysicalEnvironment(double gravitationalAcceleration) {
        this.objects = new ArrayList<>();
        this.gravitationalAcceleration = gravitationalAcceleration;
        this.gravity = false;
    }

    public ArrayList<GameObject> objects() {
        return objects;
    }

    public void setObjects(ArrayList<GameObject> objects) {
        this.objects = objects;
    }

    public double gravitationalAcceleration() {
        return gravitationalAcceleration;
    }

    public void setGravitationalAcceleration(double gravitationalAcceleration) {
        this.gravitationalAcceleration = gravitationalAcceleration;
    }

    public void addObject(GameObject obj) {
        this.objects.add(obj);
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    public void enableGravity() {
        setGravity(true);
    }

    public void disableGravity() {
        setGravity(false);
    }

    public boolean gravity() {
        return gravity;
    }

    // Try to extract the bird from the world (Hint: Returns the first found instance of a circular object
    public CircularObject extractBird() {
        AtomicReference<CircularObject> _b = new AtomicReference<>(null);
        this.objects.forEach((e) -> {
            if (e instanceof CircularObject) {
                _b.set((CircularObject) e);
            }
        });
        return _b.get();
    }

    public void update() {
        this.objects.forEach((e) -> {
            e.performBasicUpdates(this);
            e.performCustomUpdates(this);
        });
    }

}
