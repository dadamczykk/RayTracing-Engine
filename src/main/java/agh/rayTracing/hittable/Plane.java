package agh.rayTracing.hittable;

import agh.rayTracing.render.Ray;
import agh.rayTracing.materials.AbstractMaterial;
import agh.rayTracing.math.Vec3d;

import static java.lang.Math.abs;

public class Plane extends AbstractHittable{
    public Vec3d point;
    public Vec3d normal;

    public Plane(Vec3d point, Vec3d normal, AbstractMaterial material){
        this.point = point;
        this.normal = normal.unitVec();
        this.material = material;
    }
    @Override
    public boolean hit(Ray r, double tMin, double tMax, HitRecord HR) {
        double d = r.direction.dot(normal);
        if (abs(d) < 10e-6) return false;
        Vec3d pl = point.subtract(r.origin);
        double t = pl.dot(normal) / d;
        if (tMin > t || t > tMax){
            return false;
        }

        HR.normal = normal;
        HR.t = t;
        HR.p = r.at(t);
        HR.material = material;
        HR.front = true;
//        HR.setFaceNormal(r, normal);
        return true;
    }
}
