package ldgrp.RayTracingInOneWeekend.ch04;

import ldgrp.RayTracingInOneWeekend.ch03.Point3;
import ldgrp.RayTracingInOneWeekend.ch03.Vec3;

/**
 * A ray is a function:
 * P(t) = A + t * B
 * where
 * P is a 3D position along a line in 3D space
 * A is the ray origin
 * B is the ray direction
 * t is a real number
 */
public class Ray {
    private final Point3 origin;
    private final Vec3 direction;

    public Ray(Point3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3 direction() {
        return direction;
    }

    public Point3 origin() {
        return origin;
    }

    public Point3 at(double t) {
        return new Point3(origin.add(direction.multiply(t)));
    }
}
