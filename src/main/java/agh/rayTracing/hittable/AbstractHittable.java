package agh.rayTracing.hittable;

import agh.rayTracing.Ray;
import agh.rayTracing.math.Vec3d;

public class AbstractHittable implements IHittable {
    // material

    // lightsource on of

    Vec3d color;

    // light rozpraszanie

    @Override
    public boolean hit(Ray r, double tMin, double tMax, HitRecord HR) {
        return false;
    }
}
