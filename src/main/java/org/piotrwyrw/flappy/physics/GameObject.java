package org.piotrwyrw.flappy.physics;

public class GameObject {

    private Vector acceleration;
    private Vector velocity;
    private Vector location;

    public GameObject(Vector acceleration, Vector velocity, Vector location) {
        this.acceleration = acceleration;
        this.velocity = velocity;
        this.location = location;
    }

    public GameObject(Vector location) {
        this.location = location;
        this.velocity = new Vector(0.0, 0.0);
        this.acceleration = new Vector(0.0, 0.0);
    }

    public Vector acceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public Vector velocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector location() {
        return location;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public void performBasicUpdates(PhysicalEnvironment env) {
        this.location.add(this.velocity);
        this.velocity.add(this.acceleration);
        if (env.gravity())
            this.acceleration.setY(env.gravitationalAcceleration());
    }

    public void jump(double force) {
        this.velocity().setY(-(force));
    }

    public void pushLeft(double force) {
        this.velocity.subX(force);
    }

    public void pushRight(double force) {
        this.velocity.addX(force);
    }

    public void pushUp(double force) {
        this.velocity.subY(force);
    }

    public void pushDown(double force) {
        this.velocity.addY(force);
    }

    // Meant to @Override

    public void performCustomUpdates(PhysicalEnvironment env) {
        return;
    }
    // Meant to @Override

    public boolean customState(PhysicalEnvironment env) {
        return false;
    }
    // Meant to @Override

    public boolean collidesWith(GameObject other) {
        return false;
    }


}
