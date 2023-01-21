package agh.rayTracing.hittable;

import agh.rayTracing.render.Ray;
import agh.rayTracing.materials.AbstractMaterial;
import agh.rayTracing.math.Vec3d;


public class HitRecord {

    public Vec3d p;
    public Vec3d normal;
    public AbstractMaterial material;
    double t;
    public boolean front;



    void setFaceNormal(Ray r, Vec3d outward){
        front = (r.direction.dot(outward) < 0);
        normal = front ? outward : outward.opposite();
    }
}
