package agh.rayTracing.hittable;

import agh.rayTracing.render.Ray;
import agh.rayTracing.materials.AbstractMaterial;

public abstract class AbstractHittable implements IHittable {

    // lightsource on of


    // light rozpraszanie

    public AbstractMaterial material;

    @Override
    public abstract boolean hit(Ray r, double tMin, double tMax, HitRecord HR);
}
