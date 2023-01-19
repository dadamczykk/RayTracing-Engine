package agh.rayTracing;

import agh.rayTracing.gui.Visualizer;
import agh.rayTracing.math.Vec3d;
import javafx.application.Platform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.sqrt;

public class Engine implements Runnable{

    Visualizer vis;
    int outWidth;
    int outHeight;


    int guiWidth;
    int guiHeight;


    public Engine(int guiWidth, int guiHeight, int outWidth, int outHeight){

        this.vis = new Visualizer(guiWidth, guiHeight, outWidth, outHeight);
        this.guiWidth = guiWidth;
        this.guiHeight = guiHeight;
        this.outHeight = outHeight;
        this.outWidth = outWidth;
    }

    public double hitSphere(Vec3d center, double rad, Ray r){
        Vec3d oc = r.origin.subtract(center);
        double a = r.direction.lengthSquared();
        double halfB = oc.dot(r.direction);
        double c = oc.lengthSquared() - rad * rad;
        double discriminant = halfB * halfB - a * c;
        if (discriminant < 0){
            return -1.0;
        } else{
            return (-halfB - sqrt(discriminant)) / a;
        }

    }

    public Vec3d bgColor(Ray r){
        double t = hitSphere(new Vec3d(0,0,-1), 0.5, r);
        if (t > 0){
//            System.out.println("sphereee");
            Vec3d N = r.at(t).subtract(new Vec3d(0,0,-1.0)).unitVec();
            return new Vec3d(N.x+1, N.y+1, N.z+1).multiply(0.5);
        }

        Vec3d unitDir = r.direction.unitVec();
        t = 0.5 * (unitDir.y + 1.0);
        return ((new Vec3d(1.0, 1.0, 1.0)).multiply(1.0-t))
                .add(new Vec3d(0.5, 0.7, 1.0).multiply(t));
    }

    public synchronized void run(){

        StringBuilder str = new StringBuilder();
        str.append("P3\n");
        str.append(outWidth).append(" ").append(outHeight).append("\n").append(255).append("\n");

        double aspectRatio = ((float) outWidth) / outHeight;

        System.out.println(aspectRatio);

        double viewportHeight = 2.0;
        double viewportWidth = aspectRatio * viewportHeight;
        double focalLength = 1.0;

        System.out.println(viewportWidth);

        Vec3d origin = new Vec3d(0.0,0.0,0.0);
        Vec3d horizontal = new Vec3d(viewportWidth, 0.0,0.0);
        Vec3d vertical = new Vec3d(0.0, viewportHeight, 0.0);
        Vec3d lowerLeftCorner = new Vec3d(0.0,0.0,0.0);
        lowerLeftCorner.subtractSelf(horizontal.divide(2.0));
        lowerLeftCorner.subtractSelf(vertical.divide(2.0));
        lowerLeftCorner.subtractSelf(new Vec3d(0.0, 0.0, focalLength));

        for (int y = outHeight -1; y >= 0 ; y--){
//            try {
//                    wait(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            int finalY = y;
            for (int x = 0; x < outWidth; x++){

                double u = (double) x / (outWidth-1);
                double v = (double) y / (outHeight-1);

                Ray r = new Ray(origin, ((lowerLeftCorner.add(horizontal.multiply(u)))
                        .add(vertical.multiply(v))).subtract(origin));
                Vec3d col = bgColor(r);


                int finalX = x;
                str.append(col.getColor());;

                Platform.runLater(() -> {
                    vis.writePixel(finalX, finalY, col);
//                    vis.paintImg(finalY);
                });

            }

        }
//        Platform.runLater(() -> {
//            vis.paintImg(0);
//        });
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
