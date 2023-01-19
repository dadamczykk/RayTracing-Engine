package agh.rayTracing.hittable;

import agh.rayTracing.Ray;

public interface IHittable {
    public boolean hit(Ray r, double tMin, double tMax, HitRecord HR);

}
