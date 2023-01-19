package agh.rayTracing.materials;

import agh.rayTracing.Main;
import agh.rayTracing.Ray;
import agh.rayTracing.hittable.HitRecord;
import agh.rayTracing.math.Vec3d;

public class Lambertian extends AbstractMaterial{

    public Vec3d col;

    public Lambertian(Vec3d col){
        this.col = col;
    }


    @Override
    public boolean scatter(Ray r, HitRecord hr, Vec3d reduced, Ray scatter) {

        Vec3d scatterDir = hr.normal.add(Vec3d.randomUnitVec());

        if (scatterDir.nearZero()){
            scatterDir = hr.normal;
        }

        scatter.origin = hr.p;
        scatter.direction = scatterDir;

        reduced.x = col.x;
        reduced.y = col.y;
        reduced.z = col.z;
        return true;
    }

}
