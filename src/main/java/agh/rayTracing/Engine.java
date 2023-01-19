package agh.rayTracing;

import agh.rayTracing.gui.Visualizer;
import agh.rayTracing.math.Vec3d;
import javafx.application.Platform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Engine implements Runnable{

    Visualizer vis;
    int width;
    int height;

    int rowsNo;

    int colsNo;

    int pixelWidth;
    int pixelHeight;


    public Engine(int width, int height, int pixelWidth, int pixelHeight){

        this.vis = new Visualizer(width, height, pixelWidth, pixelHeight);
        this.width = width;
        this.height = height;
        this.pixelHeight = pixelHeight;
        this.pixelWidth = pixelWidth;
        this.rowsNo = height / pixelHeight;
        this.colsNo = width / pixelWidth;
    }

    public boolean hitSphere(Vec3d center, double rad, Ray r){
        Vec3d oc = r.origin.substract(center);
        double a = r.direction.dot(r.direction);
        double b = 2.0 * oc.dot(r.direction);
        double c = oc.dot(oc) - rad * rad;
        double discriminant = b * b - 4 * a * c;
        return (discriminant > 0);
    }

    public Vec3d bgColor(Ray r){
        if (hitSphere(new Vec3d(0,0,-1), 0.5, r)){
//            System.out.println("sphereee");
            return new Vec3d(1.0, 0.0, 0.0);
        }

        Vec3d unitDir = r.direction.unitVec();
        double t = 0.5 * (unitDir.y + 1.0);
        return ((new Vec3d(1.0, 1.0, 1.0)).multiply(1.0-t))
                .add(new Vec3d(0.5, 0.7, 1.0).multiply(t));
    }

    public synchronized void run(){

        StringBuilder str = new StringBuilder();
        str.append("P3\n");
        str.append(colsNo).append(" ").append(rowsNo).append("\n").append(255).append("\n");

        double aspectRatio = ((float) width) / height;

        System.out.println(aspectRatio);

        double viewportHeight = 2.0;
        double viewportWidth = aspectRatio * viewportHeight;
        double focalLength = 1.0;

        System.out.println(viewportWidth);

        Vec3d origin = new Vec3d(0.0,0.0,0.0);
        Vec3d horizontal = new Vec3d(viewportWidth, 0.0,0.0);
        Vec3d vertical = new Vec3d(0.0, viewportHeight, 0.0);
        Vec3d lowerLeftCorner = new Vec3d(0.0,0.0,0.0);
        lowerLeftCorner.substractSelf(horizontal.divide(2.0));
        lowerLeftCorner.substractSelf(vertical.divide(2.0));
        lowerLeftCorner.substractSelf(new Vec3d(0.0, 0.0, focalLength));

        for (int y = rowsNo -1; y >= 0 ; y--){
            for (int x = 0; x < colsNo; x++){

                double u = (double) x / (width-1);
                double v = (double) y / (height-1);

                Ray r = new Ray(origin, ((lowerLeftCorner.add(horizontal.multiply(u)))
                        .add(vertical.multiply(v))).substract(origin));
                Vec3d col = bgColor(r);
//                System.out.println(col);
//                try {
//                    wait(10);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                int finalY = y;
                int finalX = x;
                str.append(col.getColor());;
                Platform.runLater(() -> {
                    vis.paintPixel(finalX, finalY, col);
                });
            }
        }
        System.out.println("here");
        String filename = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss'.ppm'").format(new Date());
        File out = new File(filename);
        FileWriter writer = null;
        try {
            writer = new FileWriter(out);
            writer.write(str.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
