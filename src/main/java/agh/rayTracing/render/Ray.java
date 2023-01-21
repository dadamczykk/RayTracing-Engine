package agh.rayTracing.render;

import agh.rayTracing.materials.IMaterial;
import agh.rayTracing.materials.Metal;
import agh.rayTracing.math.Vec3d;


public class Ray {
    public Vec3d origin;
    public Vec3d direction;

    public Ray (Vec3d origin, Vec3d direction){
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3d at(double t){
        return origin.add(direction.multiply(t));
    }
}
