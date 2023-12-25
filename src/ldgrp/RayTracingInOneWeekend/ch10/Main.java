package ldgrp.RayTracingInOneWeekend.ch10;

import ldgrp.RayTracingInOneWeekend.ch03.Vec3;
import ldgrp.RayTracingInOneWeekend.ch03.Point3;
import ldgrp.RayTracingInOneWeekend.ch06.HittableList;
import ldgrp.RayTracingInOneWeekend.ch06.Sphere;
import ldgrp.RayTracingInOneWeekend.ch07.Camera;
import ldgrp.RayTracingInOneWeekend.ch11.Dielectric;

public class Main {
    public static void main(String[] args) {
        var world = new HittableList();

        var materialGround = new Lambertian(new Vec3(0.8, 0.8, 0.0));
        var materialCenter = new Lambertian(new Vec3(0.1, 0.2, 0.5));
        var materialLeft = new Dielectric(1.5);
        var materialRight = new Metal(new Vec3(0.8, 0.6, 0.2), 0.0);

        world.add(new Sphere(new Point3(0, -100.5, -1), 100, materialGround));
        world.add(new Sphere(new Point3(0, 0, -1), 0.5, materialCenter));
        world.add(new Sphere(new Point3(-1, 0, -1), 0.5, materialLeft));
        world.add(new Sphere(new Point3(-1, 0, -1), -0.4, materialLeft));
        world.add(new Sphere(new Point3(1, 0, -1), 0.5, materialRight));

        var camera = new Camera(1280, 720, 100, 50);
        camera.render(world);
    }
}
