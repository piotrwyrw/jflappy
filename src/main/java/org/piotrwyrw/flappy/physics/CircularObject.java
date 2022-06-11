package org.piotrwyrw.flappy.physics;

import org.piotrwyrw.flappy.Game;

public class CircularObject extends GameObject {

    private double boundingRadius;

    public CircularObject(Vector acceleration, Vector velocity, Vector location, double boundingRadius) {
        super(acceleration, velocity, location);
        this.boundingRadius = boundingRadius;
    }

    public CircularObject(Vector location, double boundingRadius) {
        super(location);
        this.boundingRadius = boundingRadius;
    }

    public double boundingRadius() {
        return boundingRadius;
    }

    public boolean alive(PhysicalEnvironment env) {
        return !customState(env);
    }

    /**
     * True - Alive
     * False - Dead (Game over)
     * @param env
     * @return
     */
    @Override
    public boolean customState(PhysicalEnvironment env) {
        return (location().y() < Game.HEIGHT);
    }

}
