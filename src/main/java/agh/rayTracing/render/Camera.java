package agh.rayTracing.render;

import agh.rayTracing.Main;
import agh.rayTracing.math.Vec3d;

import static java.lang.Math.tan;

public class Camera {

    Vec3d origin;
    Vec3d lowerLeftCorner;
    Vec3d horizontal;
    Vec3d vertical;

    public Camera(int width, int height,Vec3d lookFrom, Vec3d lookAt, Vec3d hmm, double vFov){

        double theta  = Main.degToRad(vFov);
        double h = tan(theta/2);
        double aspectRatio = (double)width / (double)height;
        double viewportHeight = 2.0 * h;
        double viewportWidth = aspectRatio * viewportHeight;

        Vec3d w = lookFrom.subtract(lookAt).unitVec();
        Vec3d u = hmm.cross(w).unitVec();
        Vec3d v = w.cross(u);

        this.origin = lookFrom;
        this.horizontal = u.multiply(viewportWidth);
        this.vertical = v.multiply(viewportHeight);
        this.lowerLeftCorner = origin.subtract(horizontal.divide(2))
                .subtract(vertical.divide(2)).subtract(w);
    }

    public Ray getRay(double u, double v){
        return new Ray(origin, lowerLeftCorner.add(horizontal.multiply(u))
                .add(vertical.multiply(v)).subtract(origin));
    }
}
