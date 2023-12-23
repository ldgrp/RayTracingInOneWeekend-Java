package ldgrp.RayTracingInOneWeekend.ch10;

import ldgrp.RayTracingInOneWeekend.ch06.HitRecord;
import ldgrp.RayTracingInOneWeekend.ch04.Ray;

public interface Material {
    ScatterRecord scatter(Ray ray, HitRecord hitRecord);
}
