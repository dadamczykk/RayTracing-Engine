package agh.rayTracing;

import agh.rayTracing.math.Vec3d;

public class Camera {

    Vec3d origin;
    Vec3d lowerLeftCorner;
    Vec3d horizontal;
    Vec3d vertical;

    public Camera(int width, int height){
        double aspectRatio = (double)width / (double)height;
        double viewportHeight = 2.0;
        double viewportWidth = aspectRatio * viewportHeight;
        double focalLength = 1.0;

        this.origin = new Vec3d(0, 0,0 );
        this.horizontal = new Vec3d(viewportWidth, 0, 0);
        this.vertical = new Vec3d(0, viewportHeight, 0);
        this.lowerLeftCorner = origin.subtract(horizontal.divide(2))
                .subtract(vertical.divide(2)).subtract(new Vec3d(0, 0, focalLength));
    }

    public Ray getRay(double u, double v){
        return new Ray(origin, lowerLeftCorner.add(horizontal.multiply(u))
                .add(vertical.multiply(v)).subtract(origin));
    }
}
