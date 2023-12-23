package ldgrp.RayTracingInOneWeekend.ch06;

import ldgrp.RayTracingInOneWeekend.ch04.Ray;

public interface Hittable {
    HitRecord hit(Ray ray, Interval rayT);
}
