package agh.rayTracing.render;

import agh.rayTracing.math.Vec3d;

public class BasicSky implements ISky{
    Vec3d col;
    public BasicSky(Vec3d col){
        this.col = col;
    }


    @Override
    public Vec3d getColor(Ray r) {
        return col;
    }
}
