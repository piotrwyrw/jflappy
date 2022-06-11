package org.piotrwyrw.flappy.physics;

import org.piotrwyrw.flappy.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Trail {

    private ArrayList<Vector> points;
    private Color color;
    private double speed;
    private int maxLength;

    public Trail(Color color, double speed, int maxLength) {
        this.points = new ArrayList<>();
        this.color = color;
        this.speed = speed;
        this.maxLength = maxLength;
    }

    public void add(Vector pos) {
        this.points.add(new Vector(pos.x(), pos.y()));
        if (this.points.size() > this.maxLength) {
            this.points.remove(0);
        }
    }

    public ArrayList<Vector> points() {
        return points;
    }

    public Color color() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void update() {
        this.points.forEach((p) -> {
            p.subX(this.speed);
        });
    }

    public void render(Graphics g) {
        AtomicInteger i = new AtomicInteger(0);
        this.points.forEach((e) -> {
            Vector v = e;
            g.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), (255/this.maxLength)*(i.intValue() + 1)));
            g.fillRect((int) v.x(), (int) v.y(), 20, 20);
            i.incrementAndGet();
        });
    }


}
