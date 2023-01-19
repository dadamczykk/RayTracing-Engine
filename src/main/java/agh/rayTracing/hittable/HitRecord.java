package agh.rayTracing.hittable;

import agh.rayTracing.Ray;
import agh.rayTracing.math.Vec3d;

public class HitRecord {
    Vec3d p;
    public Vec3d normal;
    double t;
    boolean front;

    void setFaceNormal(Ray r, Vec3d outward){
        front = (r.direction.dot(outward) < 0);
        normal = front ? outward : outward.opposite();
    }
}
