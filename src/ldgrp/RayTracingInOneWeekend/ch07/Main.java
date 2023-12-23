package ldgrp.RayTracingInOneWeekend.ch07;

import ldgrp.RayTracingInOneWeekend.ch03.Point3;
import ldgrp.RayTracingInOneWeekend.ch06.HittableList;
import ldgrp.RayTracingInOneWeekend.ch06.Sphere;

public class Main {
    public static void main(String[] args) {
        var world = new HittableList();
        world.add(new Sphere(new Point3(0, 0, -1), 0.5));
        world.add(new Sphere(new Point3(0, -100.5, -1), 100));

        var camera = new Camera(400, 225);
        camera.render(world);
    }
}
