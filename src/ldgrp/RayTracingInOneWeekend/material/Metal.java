package ldgrp.RayTracingInOneWeekend.material;

import ldgrp.RayTracingInOneWeekend.surface.HitRecord;
import ldgrp.RayTracingInOneWeekend.utilities.Ray;
import ldgrp.RayTracingInOneWeekend.utilities.Vec3;

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
