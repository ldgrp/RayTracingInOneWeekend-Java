package ldgrp.RayTracingInOneWeekend.ch05;

import ldgrp.RayTracingInOneWeekend.ch02.PPMImage;
import ldgrp.RayTracingInOneWeekend.ch03.Point3;
import ldgrp.RayTracingInOneWeekend.ch03.Vec3;
import ldgrp.RayTracingInOneWeekend.ch04.Ray;

public class Main {

    public static boolean hitSphere(Point3 center, double radius, Ray ray) {
        var oc = ray.origin().subtract(center);
        var a = ray.direction().dot(ray.direction());
        var b = 2.0 * oc.dot(ray.direction());
        var c = oc.dot(oc) - radius * radius;

        var discriminant = b * b - 4 * a * c;
        return discriminant >= 0;
    }

    public static Vec3 rayColor(Ray ray) {
        if (hitSphere(new Point3(0, 0, -1), 0.5, ray)) {
            return new Vec3(1, 0, 0);
        }
        var unitDirection = ray.direction().unitVector();
        var t = 0.5 * (unitDirection.e1() + 1.0);
        return new Vec3(1.0, 1.0, 1.0).multiply(1.0 - t).add(new Vec3(0.5, 0.7, 1.0).multiply(t));
    }

    public static void main(String[] args) {
        var aspectRatio = 16.0 / 9.0;
        var imageWidth = 400;
        var imageHeight = (int) (imageWidth / aspectRatio);
        imageHeight = Math.max(imageHeight, 1);

        // Camera
        var focalLength = 1.0;
        var viewportHeight = 2.0;
        var viewportWidth = aspectRatio * viewportHeight;
        var origin = new Point3(0, 0, 0);

        // Viewport basis vectors
        var horizontal = new Vec3(viewportWidth, 0, 0);
        var vertical = new Vec3(0, -viewportHeight, 0);

        // Delta
        var horizontalDelta = horizontal.divide(imageWidth);
        var verticalDelta = vertical.divide(imageHeight);

        // Upper left corner of the viewport
        var upperLeftCorner = origin.subtract(horizontal.divide(2)).subtract(vertical.divide(2)).subtract(new Vec3(0, 0, focalLength));
        var pixelUpperLeftCorner = horizontalDelta.add(verticalDelta).multiply(0.5).add(upperLeftCorner);

        PPMImage image = new PPMImage(imageWidth, imageHeight);

        for (var row = 0; row < imageHeight; row++) {
            for (var col = 0; col < imageWidth; col++) {
                var pixelPosition = pixelUpperLeftCorner.add(horizontalDelta.multiply(col)).add(verticalDelta.multiply(row));
                var ray = new Ray(origin, pixelPosition.subtract(origin));

                var color = rayColor(ray);
                image.setPixel(col, row, color);
            }
        }

        image.writeToFile("image.ppm");
    }
}
