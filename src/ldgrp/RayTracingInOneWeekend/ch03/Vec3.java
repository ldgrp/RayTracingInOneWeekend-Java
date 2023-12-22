package ldgrp.RayTracingInOneWeekend.ch03;

public record Vec3(double e0, double e1, double e2) {
    public Vec3 {
        if (Double.isNaN(e0) || Double.isNaN(e1) || Double.isNaN(e2)) {
            throw new IllegalArgumentException("NaN");
        }
    }

    public Vec3 add(Vec3 v) {
        return new Vec3(e0 + v.e0, e1 + v.e1, e2 + v.e2);
    }

    public Vec3 subtract(Vec3 v) {
        return new Vec3(e0 - v.e0, e1 - v.e1, e2 - v.e2);
    }

    public Vec3 multiply(Vec3 v) {
        return new Vec3(e0 * v.e0, e1 * v.e1, e2 * v.e2);
    }

    public Vec3 multiply(double t) {
        return new Vec3(t * e0, t * e1, t * e2);
    }

    public Vec3 divide(Vec3 v) {
        return new Vec3(e0 / v.e0, e1 / v.e1, e2 / v.e2);
    }

    public Vec3 divide(double t) {
        return new Vec3(e0 / t, e1 / t, e2 / t);
    }

    public double lengthSquared() {
        return e0 * e0 + e1 * e1 + e2 * e2;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vec3 unitVector() {
        return divide(length());
    }

    public double dot(Vec3 v) {
        return e0 * v.e0 + e1 * v.e1 + e2 * v.e2;
    }

    public Vec3 cross(Vec3 v) {
        return new Vec3(
            e1 * v.e2 - e2 * v.e1,
            e2 * v.e0 - e0 * v.e2,
            e0 * v.e1 - e1 * v.e0
        );
    }

    public Vec3 negate() {
        return new Vec3(-e0, -e1, -e2);
    }
}