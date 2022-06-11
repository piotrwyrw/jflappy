package org.piotrwyrw.flappy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallaxImage {

    private ArrayList<Pair<Double, BufferedImage>> layers;
    private double speed;

    public ParallaxImage(double speed) {
        this.layers = new ArrayList<>();
        this.speed = speed;
    }

    public ArrayList<Pair<Double, BufferedImage>> layers() {
        return layers;
    }

    public void addLayer(BufferedImage img) {
        this.layers.add(new Pair<>(0.0, img));
    }
    public double speed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double theta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double computeXOffset(int index, double delta) {
        if (index >= this.layers.size()) {
            return 0.0;
        }
        return -(Math.abs(layers.get(index).first()) + ((double) this.layers.size() / (index + 1)) * delta);
    }

    public void updateOffsets(double cameraX) {
        AtomicInteger i = new AtomicInteger(0);
        ArrayList<Pair<Double, BufferedImage>> rev = (ArrayList<Pair<Double, BufferedImage>>) this.layers.clone();
        Collections.reverse(rev);
        rev.forEach((e) -> {
            e.setFirst(computeXOffset(i.intValue(), cameraX));
            i.incrementAndGet();
        });
    }

    double theta = 0.0;

    public void render(Graphics2D g, int x, int y, int offset) {
        theta += 0.04;
        ArrayList<Pair<Double, BufferedImage>> rev = (ArrayList<Pair<Double, BufferedImage>>) this.layers.clone();
        Collections.reverse(rev);

        AtomicInteger i = new AtomicInteger(0);

        // Update all parallax offsets with the supplied scrolling speed
        updateOffsets(this.speed);

        rev.forEach((e) -> {
            double off = e.first();
            BufferedImage img = e.last();

            // If the image has faded off enough for the (white) frame
            // background to be seen, draw a second image over there
            if (off + img.getWidth() < Game.WIDTH) {
                g.drawImage(img, (int) off + img.getWidth(), -(img.getHeight() - Game.HEIGHT), null);

                // If the second image's X coordinate (offset) has reached X 0, set the offset of that
                // parallax layer to 0 (reset it to its default setting)
                if (off + img.getWidth() <= 0) {
                    e.setFirst(0.0);
                }
            }

            g.drawImage(img, (int) off, -(img.getHeight() - Game.HEIGHT), null);
            i.incrementAndGet();
        });
    }

}
