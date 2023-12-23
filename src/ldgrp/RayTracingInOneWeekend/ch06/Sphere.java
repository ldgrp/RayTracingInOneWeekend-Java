package ldgrp.RayTracingInOneWeekend.ch06;

import ldgrp.RayTracingInOneWeekend.ch03.Point3;
import ldgrp.RayTracingInOneWeekend.ch04.Ray;
import ldgrp.RayTracingInOneWeekend.ch10.Material;

public class Sphere implements Hittable {
    private final Point3 center;
    private final double radius;

    private final Material material;

    public Sphere(Point3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public HitRecord hit(Ray ray, Interval rayT) {
        var oc = ray.origin().subtract(center);
        var a = ray.direction().lengthSquared();
        var halfB = oc.dot(ray.direction());
        var c = oc.lengthSquared() - radius * radius;

        var discriminant = halfB * halfB - a * c;
        if (discriminant < 0) {
            return null;
        }

        var sqrtd = Math.sqrt(discriminant);

        // Find the nearest root that lies in the acceptable range.
        var root = (-halfB - sqrtd) / a;
        if (!rayT.surrounds((root))) {
            root = (-halfB + sqrtd) / a;
            if (!rayT.surrounds(root)) {
                return null;
            }
        }

        var t = root;
        var point = ray.at(t);
        var outwardNormal = point.subtract(center).divide(radius);

        var rec = new HitRecord(point, outwardNormal, this.material, t);
        rec.setFaceNormal(ray, outwardNormal);
        return rec;
    }
}
