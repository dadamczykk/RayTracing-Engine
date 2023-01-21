package agh.rayTracing.render;

import agh.rayTracing.Main;
import agh.rayTracing.gui.App;
import agh.rayTracing.gui.Visualizer;
import agh.rayTracing.hittable.*;
import agh.rayTracing.materials.*;
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

    Camera cam;

    final int samples;
    final int depth;

    HittableList hittables;

    Vec3d bgColor;

    ISky sky;

    public Engine(int guiWidth, int guiHeight, int outWidth,
                  int outHeight, int density, int depth, HittableList hittables, ISky sky, App init){

        this.vis = new Visualizer(guiWidth, guiHeight, outWidth, outHeight, init);
        this.guiWidth = guiWidth;
        this.guiHeight = guiHeight;
        this.outHeight = outHeight;
        this.outWidth = outWidth;
        cam = new Camera(outWidth, outHeight, new Vec3d(-5,5,5), // new Vec3d(-2,2,1),
                new Vec3d(0, 1, -1), new Vec3d(0,1,0), 150);
        samples = density;
        this.depth = depth;
        this.hittables = hittables;
        this.sky = sky;

    }


    public Vec3d vecCol(Ray r, HittableList all, int depth){
        HitRecord HR = new HitRecord();

        if (depth <= 0){
            return new Vec3d(0,0,0);
        }

        if (!all.hit(r, 0.001, Main.inf, HR)) {
            return sky.getColor(r);
        }
        Ray scatter = new Ray(new Vec3d(0,0,0), new Vec3d(0,0,0));

        Vec3d col = new Vec3d(0,0,0);
        Vec3d emitted = HR.material.emitted();


        if (! HR.material.scatter(r, HR, col, scatter)) {
            return emitted;
        }
        return emitted.add(col.multiply(vecCol(scatter, all, depth-1)));
        }


    private double clamp(double val, double min, double max){
        if (val < min) return min;
        return Math.min(val, max);
    }

    private Vec3d getColor(Vec3d color, int samples){
        return new Vec3d(clamp(sqrt(color.x / samples), 0, 0.999),
                clamp(sqrt(color.y / samples), 0, 0.9999),
                clamp(sqrt(color.z / samples), 0, 0.9999));
    }

    public synchronized void run(){

        StringBuilder str = new StringBuilder();
        str.append("P3\n");
        str.append(outWidth).append(" ").append(outHeight).append("\n").append(255).append("\n");


        for (int y = outHeight -1; y >= 0 ; y--){

            int finalY = y;
            for (int x = 0; x < outWidth; x++){
                if (Thread.interrupted()){
                    return;
                }
                int finalX = x;

                Vec3d col = new Vec3d(0,0,0);
                for (int k = 0; k < samples; k++){
                    double u = (x+Main.randomDouble()) / (outWidth-1);
                    double v = (y+Main.randomDouble()) / (outHeight-1);
                    Ray r = cam.getRay(u, v);
                    col.addSelf(vecCol(r, hittables, depth));
                }

                col = this.getColor(col, samples);

                str.append(col.getColor());;

                Vec3d finalCol = col;
                Platform.runLater(() -> {
                    vis.writePixel(finalX, finalY, finalCol);
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
