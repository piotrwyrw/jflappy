package org.piotrwyrw.flappy.physics;

import java.awt.font.ImageGraphicAttribute;

public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double y() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    double magnitudeSquared() {
        return x*x + y*y;
    }

    double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    public void multiply(double d) {
        this.x *= d;
        this.y *= d;
    }

    public void divide(double d) {
        this.x /= d;
        this.y /= d;
    }

    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void subtract(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void normalize() {
        double m = magnitude();
        if (m == 0.0)
            return;
        divide(m);
    }

    public void addX(double d) {
        this.x += d;
    }

    public void addY(double d) {
        this.y =+ d;
    }

    public void subX(double d) {
        this.x -= d;
    }

    public void subY(double d) {
        this.y -= d;
    }

    public double squaredDistanceTo(Vector other) {
        return Math.pow(x() - other.x(), 2.0) + Math.pow(y() - other.y(), 2.0);
    }

    public double distanceTo(Vector other) {
        return Math.sqrt(squaredDistanceTo(other));
    }

}
