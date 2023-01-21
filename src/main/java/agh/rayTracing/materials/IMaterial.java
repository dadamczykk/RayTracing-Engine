package agh.rayTracing.materials;

import agh.rayTracing.render.Ray;
import agh.rayTracing.hittable.HitRecord;
import agh.rayTracing.math.Vec3d;

public interface IMaterial {
    public boolean scatter(Ray r, HitRecord hr, Vec3d reduced, Ray scatter);
}
