package org.piotrwyrw.flappy.physics;

import org.piotrwyrw.flappy.Util;

public class Pipe extends GameObject {

    private Vector bounds;

    public Pipe(Vector acceleration, Vector velocity, Vector location, Vector bounds) {
        super(acceleration, velocity, location);
        this.bounds = bounds;
    }

    public Pipe(Vector location, Vector bounds) {
        super(location);
        this.bounds = bounds;
    }

    @Override
    public boolean collidesWith(GameObject other) {
        if (!(other instanceof Bird))
            return false;

        double cx = other.location().x();
        double cy = other.location().y();

        double rx = Util.clamp(location().x(), location().x() + bounds.x(), cx);
        double ry = Util.clamp(location().y(), location().y() + bounds.y(), cy);

        double dst = new Vector(cx, cy).distanceTo(new Vector(rx, ry));

        if (dst <= ((Bird) other).boundingRadius()) {
            return true;
        }

        return false;
    }

}
