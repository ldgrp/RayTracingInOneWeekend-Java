package ldgrp.RayTracingInOneWeekend.material;

import ldgrp.RayTracingInOneWeekend.surface.HitRecord;
import ldgrp.RayTracingInOneWeekend.utilities.Ray;
import ldgrp.RayTracingInOneWeekend.utilities.Vec3;

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
