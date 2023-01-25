package agh.rayTracing.materials;

import agh.rayTracing.math.Vec3d;

public class Lambertian extends Metal{

    public Vec3d col;

    public Lambertian(Vec3d col){
        super(col, 1);
    }


}
