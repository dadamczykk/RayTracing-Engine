package agh.rayTracing.materials;

import agh.rayTracing.Ray;
import agh.rayTracing.hittable.HitRecord;
import agh.rayTracing.math.Vec3d;

public class Light extends AbstractMaterial{


    public Light(Vec3d col){
        this.col = col;
    }

    @Override
    public boolean scatter(Ray r, HitRecord hr, Vec3d reduced, Ray scatter) {
        return false;
    }

    @Override
    public Vec3d emitted(){
//        System.out.println("ehre");
        return new Vec3d(1,1,1);
    }


}
