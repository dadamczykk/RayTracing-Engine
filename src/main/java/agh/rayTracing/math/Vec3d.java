package agh.rayTracing.math;

import agh.rayTracing.Main;

import java.util.Objects;

import static java.lang.Math.*;

public class Vec3d {
    public double x;
    public double y;
    public double z;

    public Vec3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        double eps = 0.000001;
        Vec3d vec3d = (Vec3d) o;
        return abs(vec3d.x - x) < eps && abs(vec3d.y - y) < eps && abs(vec3d.z - z) < eps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public Vec3d opposite(){
        return new Vec3d(-this.x, -this.y, -this.z);
    }

    public void subtractSelf(Vec3d other){
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }

    public void addSelf(Vec3d other){
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    public void multiplySelf(double t){
        this.x *= t;
        this.y *= t;
        this.z *= t;
    }

    public void divideSelf(double t){
        this.multiplySelf(1/t);
    }

    public double lengthSquared(){
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    public Vec3d add(Vec3d other){
        return new Vec3d(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vec3d subtract(Vec3d other){
        return new Vec3d(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vec3d multiply(double t){
        return new Vec3d(this.x * t, this.y * t, this.z * t);
    }

    public Vec3d multiply(Vec3d other){
        return new Vec3d(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vec3d divide(double t){
        return multiply(1/t);
    }

    public double dot(Vec3d other){
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vec3d cross(Vec3d other){
        return new Vec3d(this.y * other.z - this.z * other.y,
                         this.z * other.x - this.x * other.z,
                         this.x * other.y - this.y * other.x);
    }

    public Vec3d unitVec(){
        return this.divide(this.length());
    }

    public String getColor(){
        return  (int) (255.999 * this.x) + " " +
                (int) (255.999 * this.y) + " " +
                (int) (255.999 * this.z) + "\n";
    }

    public int getRed(){
        return (int) (255.999 * this.x);
    }

    public int getGreen(){
        return (int) (255.999 * this.y);
    }

    public int getBlue(){
        return (int) (255.999 * this.z);
    }

    @Override
    public String toString() {
        return x + ", " +
                y + ", " +
                z + " " ;
    }


    public static Vec3d random(double min, double max){
        return new Vec3d(Main.randomDouble(min, max), Main.randomDouble(min, max), Main.randomDouble(min, max));
    }

    public static Vec3d randomInUnitSphere(){
        while(true){
            Vec3d v = random(-1, 1);
            if (v.lengthSquared() < 1) return v;
        }
    }


    public static Vec3d reflect(Vec3d v, Vec3d n){
        return v.subtract(n.multiply(2*n.dot(v)));
    }
    public static Vec3d refract(Vec3d uv, Vec3d n, double frac){
        double cos = min(n.dot(uv.opposite()), 1);
        Vec3d r1 = uv.add(n.multiply(cos)).multiply(frac);
        Vec3d r2 = n.multiply(-sqrt(abs(1.0 - r1.lengthSquared())));
        return r1.add(r2);

    }
}
