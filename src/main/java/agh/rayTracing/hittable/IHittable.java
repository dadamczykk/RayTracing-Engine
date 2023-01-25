package agh.rayTracing.hittable;

import agh.rayTracing.render.Ray;

public interface IHittable {
    boolean hit(Ray r, double tMin, double tMax, HitRecord HR);

}
