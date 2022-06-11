package org.piotrwyrw.flappy.physics;

import org.piotrwyrw.flappy.Tools;

import java.awt.*;

public class RectangularObject extends GameObject {

    private Vector bounds;

    public RectangularObject(Vector acceleration, Vector velocity, Vector location, Vector bounds) {
        super(acceleration, velocity, location);
        this.bounds = bounds;
    }

    public RectangularObject(Vector location, Vector bounds) {
        super(location);
        this.bounds = bounds;
    }

    public Vector bounds() {
        return bounds;
    }

    @Override
    public boolean collidesWith(GameObject other) {
        if (!(other instanceof CircularObject circle)) {
            System.out.println("Warn: Unable to resolve collision.");
            return false;
        }

        double circleX = circle.location().x();
        double circleY = circle.location().y();

        double rectClosestX = Tools.clamp(location().x(), location().x() + bounds().x(), circleX);
        double rectClosestY = Tools.clamp(location().y(), location().y() + bounds().y(), circleY);

        double distanceX = rectClosestX - circleX;
        double distanceY = rectClosestY - circleY;
        double distanceSquared = distanceX * distanceX + distanceY * distanceY;

        double radiusSquared = circle.boundingRadius() * circle.boundingRadius();

        return distanceSquared <= radiusSquared;
    }

}
