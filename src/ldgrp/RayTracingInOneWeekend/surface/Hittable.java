package ldgrp.RayTracingInOneWeekend.surface;

import ldgrp.RayTracingInOneWeekend.utilities.Interval;
import ldgrp.RayTracingInOneWeekend.utilities.Ray;

public interface Hittable {
    HitRecord hit(Ray ray, Interval rayT);
}
