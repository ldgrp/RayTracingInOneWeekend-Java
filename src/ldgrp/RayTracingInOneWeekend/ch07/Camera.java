package ldgrp.RayTracingInOneWeekend.ch07;

import ldgrp.RayTracingInOneWeekend.ch02.PPMImage;
import ldgrp.RayTracingInOneWeekend.ch03.Point3;
import ldgrp.RayTracingInOneWeekend.ch03.Vec3;
import ldgrp.RayTracingInOneWeekend.ch04.Ray;
import ldgrp.RayTracingInOneWeekend.ch06.Hittable;
import ldgrp.RayTracingInOneWeekend.ch06.Interval;

public class Camera {
    private final int imageWidth;
    private final int imageHeight;
    private final double aspectRatio;
    private final Point3 origin;
    private final Vec3 upperLeftCorner;
    private final Vec3 pixelUpperLeftCorner;
    private final Vec3 horizontalDelta;
    private final Vec3 verticalDelta;

    public Camera(int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.aspectRatio = (double) imageWidth / imageHeight;
        this.origin = new Point3(0, 0, 0);

        // Camera
        var focalLength = 1.0;
        var viewportHeight = 2.0;
        var viewportWidth = aspectRatio * viewportHeight;

        // Viewport basis vectors
        var horizontal = new Vec3(viewportWidth, 0, 0);
        var vertical = new Vec3(0, -viewportHeight, 0);

        // Delta
        this.horizontalDelta = horizontal.divide(imageWidth);
        this.verticalDelta = vertical.divide(imageHeight);

        // Upper left corner of the viewport
        this.upperLeftCorner = origin.subtract(horizontal.divide(2)).subtract(vertical.divide(2)).subtract(new Vec3(0, 0, focalLength));
        this.pixelUpperLeftCorner = horizontalDelta.add(verticalDelta).multiply(0.5).add(upperLeftCorner);
    }

    public void render(Hittable world) {
        PPMImage image = new PPMImage(imageWidth, imageHeight);

        for (var row = 0; row < imageHeight; row++) {
            for (var col = 0; col < imageWidth; col++) {
                var pixelPosition = pixelUpperLeftCorner.add(horizontalDelta.multiply(col)).add(verticalDelta.multiply(row));
                var ray = new Ray(origin, pixelPosition.subtract(origin));

                var color = rayColor(ray, world);
                image.setPixel(col, row, color);
            }
        }

        image.writeToFile("image.ppm");
    }

    private static Vec3 rayColor(Ray ray, Hittable world) {
        var hitRecord = world.hit(ray, new Interval(0, Double.POSITIVE_INFINITY));
        if (hitRecord != null) {
            var normal = hitRecord.getNormal();
            return new Vec3(normal.e0() + 1, normal.e1() + 1, normal.e2() + 1).multiply(0.5);
        }
        var unitDirection = ray.direction().unitVector();
        var a = 0.5 * (unitDirection.e1() + 1.0);
        return new Vec3(1.0, 1.0, 1.0).multiply(1.0 - a).add(new Vec3(0.5, 0.7, 1.0).multiply(a));
    }
}
