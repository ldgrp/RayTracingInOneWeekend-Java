package ldgrp.RayTracingInOneWeekend.ch02;

import ldgrp.RayTracingInOneWeekend.ch03.Vec3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PPMImage {
    /** The width of the image in pixels. */
    private final int width;
    /** The height of the image in pixels. */
    private final int height;
    /** The maximum value of a color component. */
    private final int maxColorValue;

    /** The image data */
    private final int[][][] pixels;

    public PPMImage(int width, int height, int maxColorValue) {
        this.width = width;
        this.height = height;
        this.maxColorValue = maxColorValue;

        this.pixels = new int[width][height][3];
    }

    public PPMImage(int width, int height) {
        this(width, height, 255);
    }

    public void setPixel(int x, int y, int r, int g, int b) {
        pixels[x][y][0] = r;
        pixels[x][y][1] = g;
        pixels[x][y][2] = b;
    }

    public void setPixel(int x, int y, Vec3 v) {
        setPixel(x, y, (int) (this.maxColorValue * v.e0()), (int) (this.maxColorValue * v.e1()), (int) (this.maxColorValue * v.e2()));
    }


    /**
     * Draw a red-green gradient
     */
    public void drawRG() {
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++) {
                int r = (int) (255.99 * col) / (width - 1);
                int g = (int) (255.99 * row) / (height - 1);
                int b = 0;

                setPixel(col, row, r, g, b);
            }

        }
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        // Write the header
        sb.append("P3\n");
        sb.append(width);
        sb.append(" ");
        sb.append(height);
        sb.append("\n");
        sb.append(maxColorValue);
        sb.append("\n");

        // Write the pixel data
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col ++) {
                sb.append(pixels[col][row][0]);
                sb.append(" ");
                sb.append(pixels[col][row][1]);
                sb.append(" ");
                sb.append(pixels[col][row][2]);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public void writeToFile(String fileName) {
        try {
            var file = new File(fileName);
            var writer = new PrintWriter(file);
            writer.print(this.serialize());
            writer.close();
            System.out.println("Wrote file to: " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }

    }
}
