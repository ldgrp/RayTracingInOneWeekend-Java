package ldgrp.RayTracingInOneWeekend.ch10;

import ldgrp.RayTracingInOneWeekend.ch03.Vec3;
import ldgrp.RayTracingInOneWeekend.ch04.Ray;
import ldgrp.RayTracingInOneWeekend.ch06.HitRecord;

public class Lambertian implements Material {
    private final Vec3 albedo;

    public Lambertian(Vec3 albedo) {
        this.albedo = albedo;
    }

    @Override
    public ScatterRecord scatter(Ray ray, HitRecord hitRecord) {
        var scatterDirection = hitRecord.getNormal().add(Vec3.randomUnitVector());
        if (scatterDirection.nearZero()) {
            scatterDirection = hitRecord.getNormal();
        }
        var scatteredRay = new Ray(hitRecord.getPoint(), scatterDirection);
        return new ScatterRecord(this.albedo, scatteredRay);
    }
}
