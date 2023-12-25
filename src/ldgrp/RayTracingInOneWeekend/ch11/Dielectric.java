package ldgrp.RayTracingInOneWeekend.ch11;

import ldgrp.RayTracingInOneWeekend.ch03.Vec3;
import ldgrp.RayTracingInOneWeekend.ch04.Ray;
import ldgrp.RayTracingInOneWeekend.ch06.HitRecord;
import ldgrp.RayTracingInOneWeekend.ch10.Material;
import ldgrp.RayTracingInOneWeekend.ch10.ScatterRecord;

public class Dielectric implements Material {
    private double indexOfRefraction;

    public Dielectric(double indexOfRefraction) {
        this.indexOfRefraction = indexOfRefraction;
    }

    @Override
    public ScatterRecord scatter(Ray ray, HitRecord hitRecord) {
        var attenuation = new Vec3(1,1,1);
        var refractionRatio = hitRecord.getFrontFace() ? (1.0 / indexOfRefraction) : indexOfRefraction;

        var unitDirection = ray.direction().unitVector();
        double cosTheta = Math.min(unitDirection.negate().dot(hitRecord.getNormal()), 1);
        double sinTheta = Math.sqrt(1 - cosTheta * cosTheta);
        var cannotRefract = refractionRatio * sinTheta > 1.0;

        Vec3 direction;
        if (cannotRefract || reflectance(cosTheta, refractionRatio) > Math.random()) {
            direction = Vec3.reflect(unitDirection, hitRecord.getNormal());
        } else {
            direction = Vec3.refract(unitDirection, hitRecord.getNormal(), refractionRatio);
        }

        var refractedRay = new Ray(hitRecord.getPoint(), direction);
        return new ScatterRecord(attenuation, refractedRay);
    }

    private double reflectance(double cosine, double reflectanceIndex) {
        var r0 = (1-reflectanceIndex)/(1+reflectanceIndex);
        r0 = r0*r0;
        return r0 + (1-r0) * Math.pow((1-cosine), 5);
    }


}
