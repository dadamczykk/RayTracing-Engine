package agh.rayTracing;

import agh.rayTracing.gui.Visualizer;
import agh.rayTracing.hittable.HitRecord;
import agh.rayTracing.hittable.HittableList;
import agh.rayTracing.hittable.Sphere;
import agh.rayTracing.math.Vec3d;
import javafx.application.Platform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Math.sqrt;

public class Engine implements Runnable{

    Visualizer vis;
    int outWidth;
    int outHeight;

    int guiWidth;
    int guiHeight;

    Camera cam;

    final int samples;


    public Engine(int guiWidth, int guiHeight, int outWidth, int outHeight, int density){

        this.vis = new Visualizer(guiWidth, guiHeight, outWidth, outHeight);
        this.guiWidth = guiWidth;
        this.guiHeight = guiHeight;
        this.outHeight = outHeight;
        this.outWidth = outWidth;
        cam = new Camera(outWidth, outHeight);
        samples = density;

    }


    public Vec3d vecCol(Ray r, HittableList all){
        HitRecord HR = new HitRecord();
        if (all.hit(r, 0, Main.inf, HR)){
            return HR.normal.add(new Vec3d(1, 1, 1)).multiply(0.5);
        }

        Vec3d unitDir = r.direction.unitVec();
        double t = 0.5 * (unitDir.y + 1.0);
        return ((new Vec3d(1.0, 1.0, 1.0)).multiply(1.0-t))
                .add(new Vec3d(0.5, 0.7, 1.0).multiply(t));
    }

    private double clamp(double val, double min, double max){
        if (val < min) return min;
        return Math.min(val, max);
    }

    private Vec3d getColor(Vec3d color, int samples){
        return new Vec3d(clamp(color.x / samples, 0, 0.999),
                clamp(color.y / samples, 0, 0.9999),
                clamp(color.z / samples, 0, 0.9999));
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



        HittableList world = new HittableList();
        world.add(new Sphere(new Vec3d(0, 0, -1), 0.5));
        world.add(new Sphere(new Vec3d(0, -100.5, -1), 100));

        for (int y = outHeight -1; y >= 0 ; y--){
//            try {
//                    wait(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            int finalY = y;
            for (int x = 0; x < outWidth; x++){
                int finalX = x;

                Vec3d col = new Vec3d(0,0,0);
                for (int k = 0; k < samples; k++){
                    double u = (x+Main.randomDouble()) / (outWidth-1);
                    double v = (y+Main.randomDouble()) / (outHeight-1);
                    Ray r = cam.getRay(u, v);
                    col.addSelf(vecCol(r, world));
                }

                col = this.getColor(col, samples);

                str.append(col.getColor());;
//                System.out.println(col);

                Vec3d finalCol = col;
                Platform.runLater(() -> {
                    vis.writePixel(finalX, finalY, finalCol);
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
