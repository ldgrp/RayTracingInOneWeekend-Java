package ldgrp.RayTracingInOneWeekend.ch03;

public class Vec3 {
    protected final double e0;
    protected final double e1;
    protected final double e2;

    public Vec3(double e0, double e1, double e2) {
        this.e0 = e0;
        this.e1 = e1;
        this.e2 = e2;
    }

    public static Vec3 random() {
        return new Vec3(Math.random(), Math.random(), Math.random());
    }

    public static Vec3 random(double min, double max) {
        return new Vec3(
            min + (max - min) * Math.random(),
            min + (max - min) * Math.random(),
            min + (max - min) * Math.random()
        );
    }

    public double e0() {
        return e0;
    }

    public double e1() {
        return e1;
    }

    public double e2() {
        return e2;
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

    public boolean nearZero() {
        var epsilon = 1e-8;
        return Math.abs(e0) < epsilon && Math.abs(e1) < epsilon && Math.abs(e2) < epsilon;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vec3 unitVector() {
        return divide(length());
    }

    public static Vec3 randomInUnitSphere() {
        while (true) {
            var p = Vec3.random(-1, 1);
            if (p.lengthSquared() >= 1) {
                continue;
            }
            return p;
        }
    }

    public static Vec3 randomUnitVector() {
        var a = Math.random() * Math.PI * 2;
        var z = -1 + 2 * Math.random();
        var r = Math.sqrt(1 - z * z);
        return new Vec3(r * Math.cos(a), r * Math.sin(a), z);
    }

    public static Vec3 randomOnHemisphere(Vec3 normal) {
        var inUnitSphere = randomInUnitSphere();
        if (inUnitSphere.dot(normal) > 0.0) {
            return inUnitSphere;
        } else {
            return inUnitSphere.negate();
        }
    }

    public static Vec3 reflect(Vec3 v, Vec3 normal) {
        return v.subtract(normal.multiply(v.dot(normal)).multiply(2));
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

    @Override
    public String toString() {
        return String.format("Vec3(%f %f %f)", e0, e1, e2);
    }
}