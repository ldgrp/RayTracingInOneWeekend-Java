package ldgrp.RayTracingInOneWeekend.ch10;

import ldgrp.RayTracingInOneWeekend.ch03.Vec3;
import ldgrp.RayTracingInOneWeekend.ch04.Ray;
import ldgrp.RayTracingInOneWeekend.ch06.HitRecord;

public class Metal implements Material {
    private Vec3 albedo;
    private double fuzz;

    public Metal(Vec3 albedo, double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz;
    }

    @Override
    public ScatterRecord scatter(Ray ray, HitRecord hitRecord) {
        var reflectedDirection = Vec3.reflect(ray.direction().unitVector(), hitRecord.getNormal());
        var reflectedDirectionWithFuzz = reflectedDirection.add(Vec3.randomUnitVector().multiply(fuzz));
        var reflectedRay = new Ray(hitRecord.getPoint(), reflectedDirectionWithFuzz);
        if (reflectedDirectionWithFuzz.dot(hitRecord.getNormal()) > 0) {
            return new ScatterRecord(this.albedo, reflectedRay);
        }
        return null;
    }
}
