package ldgrp.RayTracingInOneWeekend.surface;

import ldgrp.RayTracingInOneWeekend.utilities.Interval;
import ldgrp.RayTracingInOneWeekend.utilities.Ray;

import java.util.ArrayList;

public class HittableList extends ArrayList<Hittable> implements Hittable {
    @Override
    public HitRecord hit(Ray ray, Interval rayT) {
        HitRecord closestHitRecord = null;
        double closestSoFar = rayT.max;
        for (Hittable hittable: this) {
            var hitRecord = hittable.hit(ray, new Interval(rayT.min, closestSoFar));
            if (hitRecord != null) {
                closestSoFar = hitRecord.getT();
                closestHitRecord = hitRecord;
            }
        }
        return closestHitRecord;
    }
}
