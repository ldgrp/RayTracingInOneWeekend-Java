package ldgrp.RayTracingInOneWeekend;

import ldgrp.RayTracingInOneWeekend.surface.Hittable;
import ldgrp.RayTracingInOneWeekend.utilities.*;

public class Camera {
    private final int imageWidth;
    private final int imageHeight;
    private final double aspectRatio;
    private final Point3 origin;
    private final Vec3 upperLeftCorner;
    private final Vec3 pixelUpperLeftCorner;
    private final Vec3 horizontalDelta;
    private final Vec3 verticalDelta;

    private final int samplesPerPixel;
    private final int maxDepth;

    private final Vec3 defocusDiskU;
    private final Vec3 defocusDiskV;
    private final double defocusAngle;

    public Camera(int imageWidth, int imageHeight, int samplesPerPixel, int maxDepth, double verticalFov, Point3 lookFrom, Point3 lookAt, Vec3 vUp, double defocusAngle, double focusDist) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.samplesPerPixel = samplesPerPixel;
        this.maxDepth = maxDepth;
        this.aspectRatio = (double) imageWidth / imageHeight;

        this.origin = lookFrom;

        // Camera
        var theta = Math.toRadians(verticalFov);
        var height = Math.tan(theta / 2);
        var viewportHeight = 2 * height * focusDist;
        var viewportWidth = aspectRatio * viewportHeight;

        var w = lookFrom.subtract(lookAt).unitVector();
        var u = vUp.cross(w).unitVector();
        var v = w.cross(u);

        // Viewport basis vectors
        var horizontal = u.multiply(viewportWidth);
        var vertical = v.negate().multiply(viewportHeight);

        // Delta
        this.horizontalDelta = horizontal.divide(imageWidth);
        this.verticalDelta = vertical.divide(imageHeight);

        // Upper left corner of the viewport
        this.upperLeftCorner = origin.subtract(w.multiply(focusDist)).subtract(horizontal.divide(2)).subtract(vertical.divide(2));
        this.pixelUpperLeftCorner = horizontalDelta.add(verticalDelta).multiply(0.5).add(upperLeftCorner);

        this.defocusAngle = defocusAngle;
        var defocusRadius = Math.tan(Math.toRadians(defocusAngle) / 2) * focusDist;
        this.defocusDiskU = u.multiply(defocusRadius);
        this.defocusDiskV = v.multiply(defocusRadius);
    }

    public void render(Hittable world, String fileName) {
        PPMImage image = new PPMImage(imageWidth, imageHeight);

        for (var row = 0; row < imageHeight; row++) {
            for (var col = 0; col < imageWidth; col++) {
                var pixelColor = new Vec3(0, 0, 0);
                for (var sample = 0; sample < this.samplesPerPixel; sample++) {
                    var ray = getRay(row, col);
                    pixelColor = pixelColor.add(rayColor(ray, 0, world));
                }

                pixelColor = pixelColor.divide(this.samplesPerPixel);
                image.setPixel(col, row, pixelColor);
            }
        }

        image.writeToFile(fileName);
    }

    private Ray getRay(int row, int col) {
        var pixelOrigin = pixelUpperLeftCorner.add(horizontalDelta.multiply(col)).add(verticalDelta.multiply(row));
        var pixelOriginSample = pixelOrigin.add(pixelSampleSquare());

        var rayOrigin = defocusAngle <= 0 ? origin : defocusDiskSample();
        var rayDirection = pixelOriginSample.subtract(rayOrigin);
        return new Ray(rayOrigin, rayDirection);
    }

    private Point3 defocusDiskSample() {
        var p = Vec3.randomInUnitDisk();
        return new Point3(origin.add(defocusDiskU.multiply(p.e0())).add(defocusDiskV.multiply(p.e1())));
    }

    private Vec3 pixelSampleSquare() {
        var x = -0.5 + Math.random();
        var y = -0.5 + Math.random();
        return horizontalDelta.multiply(x).add(verticalDelta.multiply(y));
    }

    private Vec3 rayColor(Ray ray, int depth, Hittable world) {
        if (depth >= this.maxDepth) {
            return new Vec3(0, 0, 0);
        }

        var hitRecord = world.hit(ray, new Interval(0.001, Double.POSITIVE_INFINITY));
        if (hitRecord != null) {
            var scatteredRecord = hitRecord.getMaterial().scatter(ray, hitRecord);
            if (scatteredRecord == null) {
                return new Vec3(0, 0, 0);
            }
            return scatteredRecord.attenuation().multiply(rayColor(scatteredRecord.scattered(), depth+1, world));
        }
        var unitDirection = ray.direction().unitVector();
        var a = 0.5 * (unitDirection.e1() + 1.0);
        return new Vec3(1.0, 1.0, 1.0).multiply(1.0 - a).add(new Vec3(0.5, 0.7, 1.0).multiply(a));
    }
}
