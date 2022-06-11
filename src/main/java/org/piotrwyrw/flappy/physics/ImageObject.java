package org.piotrwyrw.flappy.physics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageObject {

    private GameObject parent;
    private BufferedImage img;
    private double xOff;
    private double yOff;

    public ImageObject(GameObject parent, BufferedImage img, double xOff, double yOff) {
        this.parent = parent;
        this.img = img;
        this.xOff = xOff;
        this.yOff = yOff;
    }

    public ImageObject(GameObject parent, BufferedImage img) {
        this.parent = parent;
        this.img = img;
        this.xOff = 0.0;
        this.yOff = 0.0;
    }

    public GameObject parent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public BufferedImage img() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
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
        g.drawImage(img, (int) (parent.location().x() + xOff), (int) (parent.location().y() + yOff), null);
    }

}
