package ldgrp.RayTracingInOneWeekend.ch10;

import ldgrp.RayTracingInOneWeekend.ch03.Vec3;
import ldgrp.RayTracingInOneWeekend.ch03.Point3;
import ldgrp.RayTracingInOneWeekend.ch06.HittableList;
import ldgrp.RayTracingInOneWeekend.ch06.Sphere;
import ldgrp.RayTracingInOneWeekend.ch07.Camera;

public class Main {
    public static void main(String[] args) {
        var world = new HittableList();

        var materialGround = new Lambertian(new Vec3(0.8, 0.8, 0.0));
        var materialCenter = new Lambertian(new Vec3(0.7, 0.3, 0.3));
        var materialLeft = new Metal(new Vec3(0.8, 0.8, 0.8), 0.3);
        var materialRight = new Metal(new Vec3(0.8, 0.6, 0.2), 1.0);

        world.add(new Sphere(new Point3(0, -100.5, -1), 100, materialGround));
        world.add(new Sphere(new Point3(0, 0, -1), 0.5, materialCenter));
        world.add(new Sphere(new Point3(-1, 0, -1), 0.5, materialLeft));
        world.add(new Sphere(new Point3(1, 0, -1), 0.5, materialRight));

        var camera = new Camera(400, 225, 100, 50);
        camera.render(world);
    }
}
