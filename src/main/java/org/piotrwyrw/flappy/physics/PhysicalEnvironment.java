package org.piotrwyrw.flappy.physics;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class PhysicalEnvironment {

    private ArrayList<GameObject> objects;
    private double gravitationalAcceleration;

    public PhysicalEnvironment(double gravitationalAcceleration) {
        this.objects = new ArrayList<>();
        this.gravitationalAcceleration = gravitationalAcceleration;
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

    public Bird extractBird() {
        AtomicReference<Bird> _b = new AtomicReference<>(null);
        this.objects.forEach((e) -> {
            if (e instanceof Bird) {
                _b.set((Bird) e);
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
