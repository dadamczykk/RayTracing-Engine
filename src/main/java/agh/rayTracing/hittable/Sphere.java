package agh.rayTracing.hittable;

import agh.rayTracing.Ray;
import agh.rayTracing.math.Vec3d;

import static java.lang.Math.sqrt;

public class Sphere extends AbstractHittable{
    Vec3d center;
    double radious;

    public Sphere(Vec3d center, double radius){
        this.center = center;
        this.radious = radius;
    }

    @Override
    public boolean hit(Ray r, double tMin, double tMax, HitRecord HR){

        Vec3d oc = r.origin.subtract(center);
        double a = r.direction.lengthSquared();
        double halfB = oc.dot(r.direction);
        double c = oc.lengthSquared() - radious * radious;

        double discriminant = halfB * halfB - a * c;
        if (discriminant < 0) return false;
        double sqrtd = sqrt(discriminant);

        double root = (-halfB - sqrtd) / ((float)a);
        if (root < tMin || tMax < root){
            root = (-halfB + sqrtd) / ((float)a);
            if (root < tMin || tMax < root){
                return false;
            }
        }
        HR.t = root;
        HR.p = r.at(HR.t);
        Vec3d outward = (HR.p.subtract(center)).divide(radious);;
        HR.setFaceNormal(r, outward);

        return true;
    }


}
