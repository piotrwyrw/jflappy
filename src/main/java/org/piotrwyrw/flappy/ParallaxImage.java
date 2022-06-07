package org.piotrwyrw.flappy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallaxImage {

    private ArrayList<BufferedImage> layers;

    public ParallaxImage() {
        this.layers = new ArrayList<>();
    }

    public ArrayList<BufferedImage> layers() {
        return layers;
    }

    public void addLayer(BufferedImage img) {
        this.layers.add(img);
    }

    public double computeXOffset(int stdoff, int index, double cameraX) {
        if (index >= this.layers.size()) {
            return 0.0;
        }
        return stdoff - cameraX / (this.layers.size() - index);
//        return stdoff - cameraX;
    }

    double theta = 0.0;

    public void render(Graphics2D g, int x, int y, int offset) {
        theta += 0.01;
        ArrayList<BufferedImage> rev = (ArrayList<BufferedImage>) this.layers.clone();
        Collections.reverse(rev);

        AtomicInteger i = new AtomicInteger(0);

        rev.forEach((e) -> {
            g.drawImage(e, (int) computeXOffset(-250, i.intValue(), Math.cos(theta) * 200), -(e.getHeight() - Game.HEIGHT), null);
            i.incrementAndGet();
        });
    }

}
