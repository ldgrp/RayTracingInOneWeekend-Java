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

    private final int samplesPerPixel = 100;
    private final int maxDepth = 10;

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
        double[] reflectances = { 0.1, 0.3, 0.5, 0.7, 0.9 };
        for (var reflectance: reflectances) {
            PPMImage image = new PPMImage(imageWidth, imageHeight);

            for (var row = 0; row < imageHeight; row++) {
                for (var col = 0; col < imageWidth; col++) {
                    var pixelColor = new Vec3(0, 0, 0);
                    for (var sample = 0; sample < this.samplesPerPixel; sample++) {
                        var ray = getRay(row, col);
                        pixelColor = pixelColor.add(rayColor(ray, 0, world, reflectance));
                    }

                    pixelColor = pixelColor.divide(this.samplesPerPixel);
                    image.setPixel(col, row, pixelColor);
                }
            }

            image.writeToFile(String.format("ch09_5_gamma_corrected_%02f.ppm", reflectance));

        }
    }

    private Ray getRay(int row, int col) {
        var pixelOrigin = pixelUpperLeftCorner.add(horizontalDelta.multiply(col)).add(verticalDelta.multiply(row));
        var pixelOriginSample = pixelOrigin.add(pixelSampleSquare());

        var rayDirection = pixelOriginSample.subtract(origin);
        return new Ray(origin, rayDirection);
    }

    private Vec3 pixelSampleSquare() {
        var x = -0.5 + Math.random();
        var y = -0.5 + Math.random();
        return horizontalDelta.multiply(x).add(verticalDelta.multiply(y));
    }

    private Vec3 rayColor(Ray ray, int depth, Hittable world, double reflectance) {
        if (depth >= this.maxDepth) {
            return new Vec3(0, 0, 0);
        }

        var hitRecord = world.hit(ray, new Interval(0.001, Double.POSITIVE_INFINITY));
        if (hitRecord != null) {
            var normal = hitRecord.getNormal().add(Vec3.randomUnitVector());
            var direction = Vec3.randomOnHemisphere(normal);
            var sampledRay = new Ray(hitRecord.getPoint(), direction);
            return rayColor(sampledRay, depth+1, world, reflectance).multiply(reflectance);
        }
        var unitDirection = ray.direction().unitVector();
        var a = 0.5 * (unitDirection.e1() + 1.0);
        return new Vec3(1.0, 1.0, 1.0).multiply(1.0 - a).add(new Vec3(0.5, 0.7, 1.0).multiply(a));
    }
}
