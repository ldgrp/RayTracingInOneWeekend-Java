package ldgrp.RayTracingInOneWeekend.ch03;

public class Point3 extends Vec3 {
    public Point3(Vec3 vec3) {
        super(vec3.e0, vec3.e1, vec3.e2);
    }

    public Point3(double e0, double e1, double e2) {
        super(e0, e1, e2);
    }
}