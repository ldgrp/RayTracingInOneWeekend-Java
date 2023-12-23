package ldgrp.RayTracingInOneWeekend.ch06;

public class Interval {
    public final double min;
    public final double max;

    public Interval() {
        this.min = Double.POSITIVE_INFINITY;
        this.max = Double.NEGATIVE_INFINITY;
    }

    public Interval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public boolean contains(double t) {
        return t >= min && t <= max;
    }

    public boolean surrounds(double t) {
        return t > min && t < max;
    }

    public double clamp(double t) {
        return Math.max(min, Math.min(max, t));
    }


    public static Interval newEmpty() {
        return new Interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    public static Interval newUniverse() {
        return new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
}
