package agh.rayTracing.hittable;

import agh.rayTracing.Ray;
import agh.rayTracing.materials.AbstractMaterial;
import agh.rayTracing.math.Vec3d;

public abstract class AbstractHittable implements IHittable {

    // lightsource on of


    // light rozpraszanie

    public AbstractMaterial material;

    @Override
    public abstract boolean hit(Ray r, double tMin, double tMax, HitRecord HR);
}
