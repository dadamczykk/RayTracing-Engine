package agh.rayTracing.materials;

import agh.rayTracing.Main;
import agh.rayTracing.Ray;
import agh.rayTracing.hittable.HitRecord;
import agh.rayTracing.math.Vec3d;

import static java.lang.Math.*;

public class Glass extends  AbstractMaterial{

    double refractionInd;


    public Glass(double ri){
        this(ri, new Vec3d(1,1,1));
    }

    public Glass(double ri, Vec3d col){
        this.col = col;
        this.refractionInd = ri;
    }

    @Override
    public boolean scatter(Ray r, HitRecord hr, Vec3d reduced, Ray scatter) {
        reduced.x = col.x;
        reduced.y = col.y;
        reduced.z = col.z;

        double refRatio = hr.front ? (1/refractionInd) : refractionInd;

        Vec3d uniDir = r.direction.unitVec();
        double cos = min(hr.normal.dot(uniDir.opposite()), 1);
        double sin = sqrt(1 - cos*cos);

        Vec3d dir;

        if (refRatio * sin > 1 || reflactance(cos, refRatio) > Main.randomDouble()){
            dir = Vec3d.reflect(uniDir, hr.normal);
        } else{
            dir = Vec3d.refract(uniDir, hr.normal, refRatio);
        }

        scatter.origin = hr.p;
        scatter.direction = dir;
        return true;
    }

    private static double reflactance(double cos, double refIdx){
        double r = (1-refIdx) / (1+refIdx);
        r *= r;
        return r + (1-r)*pow((1-cos), 5);
    }

    @Override
    public Vec3d emitted(){
//        System.out.println("ehre");
        return new Vec3d(0,0,0);
    }
}
