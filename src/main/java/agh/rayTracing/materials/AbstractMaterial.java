package agh.rayTracing.materials;

import agh.rayTracing.render.Ray;
import agh.rayTracing.hittable.HitRecord;
import agh.rayTracing.math.Vec3d;

public abstract class AbstractMaterial implements  IMaterial{

    public Vec3d col = new Vec3d(0,0,0);
    public double scatter;

    public abstract boolean scatter(Ray r, HitRecord hr, Vec3d reduced, Ray scatter);

    public Vec3d emitted(){
        return new Vec3d(0,0,0);
    }
}
