package agh.rayTracing.hittable;

import agh.rayTracing.render.Ray;
import agh.rayTracing.materials.AbstractMaterial;
import agh.rayTracing.math.Vec3d;

import static java.lang.Math.abs;


public class Triangle extends AbstractHittable{
    public Vec3d a;
    public Vec3d b;
    public Vec3d c;


    public Triangle (Vec3d a, Vec3d b, Vec3d c, AbstractMaterial material){
        this.a = a;
        this.b = b;
        this.c = c;
        this.material = material;
    }

    @Override
    public boolean hit(Ray r, double tMin, double tMax, HitRecord HR) {
        Vec3d v1 = b.subtract(a);
        Vec3d v2 = c.subtract(a);
        Vec3d N = v1.cross(v2);
        double area = N.length();

        double NdotRay = N.dot(r.direction);
        if (abs(NdotRay) < 0.000001){
            return false;
        }
        double d = -N.dot(a);

        double t = -(N.dot(r.origin) + d) / NdotRay;
        if (t < tMin || tMax < t){
            return false;
        }
        Vec3d P = r.origin.add(r.direction.multiply(t));

        Vec3d C;
        Vec3d edgeab = b.subtract(a);
        Vec3d pa = P.subtract(a);
        C = edgeab.cross(pa);
        if (N.dot(C) < 0 ){
            return false;
        }

        Vec3d edgebc = c.subtract(b);
        Vec3d pb = P.subtract(b);
        C = edgebc.cross(pb);
        if (N.dot(C) < 0 ){
            return false;
        }

        Vec3d edgeca = a.subtract(c);
        Vec3d pc = P.subtract(c);
        C = edgeca.cross(pc);
        if (N.dot(C) < 0 ){
            return false;
        }

        HR.t = t;
        HR.p = r.at(t);
        HR.material = material;
        HR.setFaceNormal(r, N);
//        HR.front = true;
        return true;

    }
}
