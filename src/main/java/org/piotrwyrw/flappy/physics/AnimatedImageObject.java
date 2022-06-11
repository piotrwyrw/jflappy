package org.piotrwyrw.flappy.physics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimatedImageObject {

    private GameObject parent;
    private BufferedImage img1;
    private BufferedImage img2;
    private double xOff;
    private double yOff;

    public AnimatedImageObject(GameObject parent, BufferedImage img1, BufferedImage img2, double xOff, double yOff) {
        this.parent = parent;
        this.img1 = img1;
        this.img2 = img2;
        this.xOff = xOff;
        this.yOff = yOff;
    }

    public AnimatedImageObject(GameObject parent, BufferedImage img1, BufferedImage img2) {
        this.parent = parent;
        this.img1 = img1;
        this.img2 = img2;
        this.xOff = 0.0;
        this.yOff = 0.0;
    }

    public GameObject parent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public BufferedImage img1() {
        return img1;
    }

    public void setImg1(BufferedImage img1) {
        this.img1 = img1;
    }

    public BufferedImage img2() {
        return img2;
    }

    public void setImg2(BufferedImage img2) {
        this.img2 = img2;
    }

    public double xOff() {
        return xOff;
    }

    public void setXOff(double xOff) {
        this.xOff = xOff;
    }

    public double yOff() {
        return yOff;
    }

    public void setYOff(double yOff) {
        this.yOff = yOff;
    }

    public void render(Graphics g) {
        BufferedImage bi = img1;
        if (parent.velocity().y() < 0.0) {
            bi = img2;
        }
        g.drawImage(bi, (int) (parent.location().x() + xOff), (int) (parent.location().y() + yOff), null);
    }

}
