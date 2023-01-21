package agh.rayTracing.render;

import agh.rayTracing.math.Vec3d;

public interface ISky {

    public Vec3d getColor(Ray r);

}
