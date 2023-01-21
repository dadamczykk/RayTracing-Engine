package agh.rayTracing.materials;

import agh.rayTracing.render.Ray;
import agh.rayTracing.hittable.HitRecord;
import agh.rayTracing.math.Vec3d;

public class Metal extends  AbstractMaterial{


    public Metal(Vec3d a, double f){
        this.col = a;
        scatter = f < 1 ? f : 1;
    }

    @Override
    public boolean scatter(Ray r, HitRecord hr, Vec3d reduced, Ray scatter) {
        Vec3d reflect = Vec3d.reflect(r.direction.unitVec(), hr.normal);
        scatter.origin = hr.p;
        scatter.direction = reflect.add(Vec3d.randomInUnitSphere().multiply(this.scatter));

        reduced.x = col.x;
        reduced.y = col.y;
        reduced.z = col.z;
        return (scatter.direction.dot(hr.normal)) > 0;
    }
}
