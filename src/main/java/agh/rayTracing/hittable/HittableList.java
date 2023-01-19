package agh.rayTracing.hittable;

import agh.rayTracing.Ray;

import java.util.ArrayList;

public class HittableList extends AbstractHittable{

    public ArrayList<AbstractHittable> objects = new ArrayList<>();

    @Override
    public boolean hit(Ray r, double tMin, double tMax, HitRecord HR){
        HitRecord tmp = new HitRecord();
        boolean wasHit = false;
        double closest = tMax;

        for (AbstractHittable object : objects){
            if (object.hit(r, tMin, closest, tmp)){
                wasHit = true;

                closest = tmp.t;
                HR = tmp;
            }
        }
        return wasHit;
    }
}
