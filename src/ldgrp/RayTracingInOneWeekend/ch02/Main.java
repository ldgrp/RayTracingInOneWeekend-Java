package ldgrp.RayTracingInOneWeekend.ch02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    /**
     * A simple program that writes a PPM image file.
     * The image is a red-green gradient.
     * The program takes one optional argument: the name of the file to write.
     * If no argument is given, the file is named "image.ppm".
     * @param args The command line arguments.
     */
    public static void main (String[] args) {
        var fileName = args.length > 0 ? args[0] : "image.ppm";

        PPMImage image = new PPMImage(256, 256);
        image.drawRG();
        image.writeToFile(fileName);
    }
}
