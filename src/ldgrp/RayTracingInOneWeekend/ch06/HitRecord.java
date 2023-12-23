package ldgrp.RayTracingInOneWeekend.ch06;

import ldgrp.RayTracingInOneWeekend.ch03.Point3;
import ldgrp.RayTracingInOneWeekend.ch03.Vec3;
import ldgrp.RayTracingInOneWeekend.ch04.Ray;

public class HitRecord {
    private final Point3 point;
    private final double t;
    private Vec3 normal;

    public HitRecord(Point3 point, Vec3 normal, double t) {
        this.point = point;
        this.normal = normal;
        this.t = t;
    }

    public Point3 getPoint() {
        return point;
    }

    public Vec3 getNormal() {
        return normal;
    }

    public double getT() {
        return t;
    }

    public void setFaceNormal(Ray ray, Vec3 outwardNormal) {
        boolean frontFace = ray.direction().dot(outwardNormal) < 0;
        this.normal = frontFace ? outwardNormal : outwardNormal.negate();
    }
}
