package org.piotrwyrw.flappy;

public class Util {

    public static double clamp(double min, double max, double x) {
        return (x > max) ? max : (x < min) ? min : x;
    }

}
