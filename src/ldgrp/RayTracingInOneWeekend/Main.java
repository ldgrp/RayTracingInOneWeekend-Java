package ldgrp.RayTracingInOneWeekend;

import ldgrp.RayTracingInOneWeekend.surface.HittableList;
import ldgrp.RayTracingInOneWeekend.surface.Sphere;
import ldgrp.RayTracingInOneWeekend.utilities.Point3;
import ldgrp.RayTracingInOneWeekend.utilities.Vec3;
import ldgrp.RayTracingInOneWeekend.material.Dielectric;
import ldgrp.RayTracingInOneWeekend.material.Lambertian;
import ldgrp.RayTracingInOneWeekend.material.Metal;

public class Main {
    public static void main(String[] args) {
        var world = new HittableList();

        var materialGround = new Lambertian(new Vec3(0.5, 0.5, 0.5));
        world.add(new Sphere(new Point3(0, -1000, 0), 1000, materialGround));

        for (var a = -11; a < 11; a++) {
            for (var b = -11; b < 11; b++) {
                var chooseMaterial = Math.random();
                var center = new Point3(a + 0.9 * Math.random(), 0.2, b + 0.9 * Math.random());

                if (center.subtract(new Point3(4, 0.2, 0)).length() > 0.9) {
                    if (chooseMaterial < 0.8) {
                        var albedo = Vec3.random().multiply(Vec3.random());
                        var sphereMaterial = new Lambertian(albedo);
                        world.add(new Sphere(center, 0.2, sphereMaterial));
                    } else if (chooseMaterial < 0.95) {
                        var albedo = Vec3.random(0.5, 1);
                        var fuzz = Math.random() * 0.5;
                        var sphereMaterial = new Metal(albedo, fuzz);
                        world.add(new Sphere(center, 0.2, sphereMaterial));
                    } else {
                        var sphereMaterial = new Dielectric(1.5);
                        world.add(new Sphere(center, 0.2, sphereMaterial));
                    }
                }
            }
        }

        var material1 = new Dielectric(1.5);
        world.add(new Sphere(new Point3(0, 1, 0), 1.0, material1));

        var material2 = new Lambertian(new Vec3(0.4, 0.2, 0.1));
        world.add(new Sphere(new Point3(-4, 1, 0), 1.0, material2));

        var material3 = new Metal(new Vec3(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Point3(4, 1, 0), 1.0, material3));

        var camera = new Camera(1280, 720, 10, 50, 20, new Point3(13, 2, 3), new Point3(0, 0, 0), new Vec3(0, 1, 0), 0.60, 10);
        camera.render(world, "output.ppm");
    }
}
