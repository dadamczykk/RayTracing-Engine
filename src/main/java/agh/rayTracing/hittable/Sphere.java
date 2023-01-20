package agh.rayTracing.hittable;

import agh.rayTracing.Ray;
import agh.rayTracing.materials.AbstractMaterial;
import agh.rayTracing.math.Vec3d;
import javafx.scene.paint.Material;

import static java.lang.Math.sqrt;

public class Sphere extends AbstractHittable{
    public Vec3d center;
    public double radious;




    public Sphere(Vec3d center, double radius, AbstractMaterial material){
        this.center = center;
        this.radious = radius;
        this.material = material;
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
//        System.out.println("material");

        HR.material = this.material;
//        System.out.println(this.material);

        return true;
    }


}
