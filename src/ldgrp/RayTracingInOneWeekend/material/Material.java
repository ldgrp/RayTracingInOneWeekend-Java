package ldgrp.RayTracingInOneWeekend.material;

import ldgrp.RayTracingInOneWeekend.surface.HitRecord;
import ldgrp.RayTracingInOneWeekend.utilities.Ray;

public interface Material {
    ScatterRecord scatter(Ray ray, HitRecord hitRecord);
}
