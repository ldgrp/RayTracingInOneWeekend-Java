package ldgrp.RayTracingInOneWeekend.surface;

import ldgrp.RayTracingInOneWeekend.utilities.Point3;
import ldgrp.RayTracingInOneWeekend.utilities.Ray;
import ldgrp.RayTracingInOneWeekend.utilities.Vec3;
import ldgrp.RayTracingInOneWeekend.material.Material;

public class HitRecord {
    private final Point3 point;
    private final double t;
    private Vec3 normal;
    private Material material;
    private boolean frontFace;

    public HitRecord(Point3 point, Vec3 normal, Material material, double t) {
        this.point = point;
        this.normal = normal;
        this.material = material;
        this.t = t;
    }

    public Point3 getPoint() {
        return point;
    }

    public Vec3 getNormal() {
        return normal;
    }

    public Material getMaterial() {
        return material;
    }

    public double getT() {
        return t;
    }

    public boolean getFrontFace() {
        return frontFace;
    }

    public void setFaceNormal(Ray ray, Vec3 outwardNormal) {
        this.frontFace = ray.direction().dot(outwardNormal) < 0;
        this.normal = frontFace ? outwardNormal : outwardNormal.negate();
    }
}
