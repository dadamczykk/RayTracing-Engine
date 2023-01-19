package agh.rayTracing.math;

public class Vec3d {
    public double x;
    public double y;
    public double z;

    public Vec3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
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
        return  Double.toString(x) + ", " +
                Double.toString(y) + ", " +
                Double.toString(z) + " " ;
    }
}
