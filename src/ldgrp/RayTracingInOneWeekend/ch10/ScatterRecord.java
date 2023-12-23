package ldgrp.RayTracingInOneWeekend.ch10;

import ldgrp.RayTracingInOneWeekend.ch03.Vec3;
import ldgrp.RayTracingInOneWeekend.ch04.Ray;

public record ScatterRecord(Vec3 attenuation, Ray scattered) {
}
