package ldgrp.RayTracingInOneWeekend.material;

import ldgrp.RayTracingInOneWeekend.utilities.Ray;
import ldgrp.RayTracingInOneWeekend.utilities.Vec3;

public record ScatterRecord(Vec3 attenuation, Ray scattered) {
}
