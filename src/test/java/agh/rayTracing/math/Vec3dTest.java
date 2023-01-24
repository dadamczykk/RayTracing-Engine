package agh.rayTracing.math;

import org.junit.jupiter.api.Test;
import java.util.Random;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Vec3dTest { //testy sprawdzam na int, chociaż wektory obsługują double. Robię tak ze względu na
    // możliwy brak precyzji typów zmiennoprzecinkowych

    @Test
    public void oppositeTest(){
            Random rand = new Random();
            for (int i = 0; i < 10; i++){
                double x = rand.nextDouble();
                double y = rand.nextDouble();
                double z = rand.nextDouble();
                Vec3d v1 = new Vec3d(x,y,z);
                Vec3d v2 = new Vec3d(-x,-y,-z);
                assertEquals(v1.opposite(), v2);
            }
    }

    @Test
    public void subtractTest(){
        Random rand = new Random();
        for (int i = 0; i < 10; i++){
            double x = rand.nextDouble() % 10000;
            double y = rand.nextDouble() % 10000;
            double z = rand.nextDouble() % 10000;

            double x1 = rand.nextDouble() % 10000;
            double y1 = rand.nextDouble() % 10000;
            double z1 = rand.nextDouble() % 10000;

            Vec3d tos = new Vec3d(x, y, z);
            Vec3d tos2 = new Vec3d(x1, y1, z1);
            Vec3d toCh = new Vec3d(x-x1, y-y1, z-z1);
            assertEquals(tos.subtract(tos2), toCh);
            tos.subtractSelf(tos2);
            assertEquals(tos, toCh);
        }
    }

    @Test
    public void addTest(){
        Random rand = new Random();
        for (int i = 0; i < 10; i++){
            double x = rand.nextDouble() % 10000;
            double y = rand.nextDouble() % 10000;
            double z = rand.nextDouble() % 10000;

            double x1 = rand.nextDouble() % 10000;
            double y1 = rand.nextDouble() % 10000;
            double z1 = rand.nextDouble() % 10000;

            Vec3d tos = new Vec3d(x, y, z);
            Vec3d tos2 = new Vec3d(x1, y1, z1);
            Vec3d toCh = new Vec3d(x+x1, y+y1, z+z1);
            assertEquals(tos.add(tos2), toCh);
            tos.addSelf(tos2);
            assertEquals(tos, toCh);
        }
    }

    @Test
    public void multiplyTest(){
        Random rand = new Random();
        for (int i = 0; i < 10; i++){
            double x = rand.nextDouble() % 1000;
            double y = rand.nextDouble() % 1000;
            double z = rand.nextDouble() % 1000;

            double x1 = rand.nextDouble() % 1000;


            Vec3d tos = new Vec3d(x, y, z);
            Vec3d toCh = new Vec3d(x * x1, y * x1, z*x1);
            assertEquals(tos.multiply(x1), toCh);
            tos.multiplySelf(x1);
            assertEquals(tos, toCh);
        }
    }

    @Test
    public void divideTest(){
        Random rand = new Random();
        for (int i = 0; i < 10; i++){
            double x = rand.nextInt() % 10000;
            double y = rand.nextInt() % 10000;
            double z = rand.nextInt() % 10000;

            double x1 = rand.nextInt() % 10000;


            Vec3d tos = new Vec3d(x, y, z);
            Vec3d toCh = new Vec3d(x / x1, y / x1, z/x1);
            assertEquals(tos.divide(x1), toCh);
            tos.divideSelf(x1);
            assertEquals(tos, toCh);
        }
    }

    @Test
    public void lengthTest() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            double x = rand.nextInt() % 10000;
            double y = rand.nextInt() % 10000;
            double z = rand.nextInt() % 10000;


            Vec3d tos = new Vec3d(x, y, z);
            double val = x * x + y * y + z * z;
            assertEquals(tos.lengthSquared(), val, 0.0001);
            assertEquals(tos.length(), sqrt(val), 0.0001);
        }
    }

    @Test
    public void dotTest() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            double x = rand.nextInt() % 10000;
            double y = rand.nextInt() % 10000;
            double z = rand.nextInt() % 10000;

            double x1 = rand.nextInt() % 10000;
            double y1 = rand.nextInt() % 10000;
            double z1 = rand.nextInt() % 10000;


            Vec3d tos = new Vec3d(x, y, z);
            Vec3d tos2 = new Vec3d(x1, y1, z1);

            double val = x * x1 + y * y1 + z * z1;
            assertEquals(tos.dot(tos2), val, 0.0001);
            assertEquals(tos2.dot(tos), val, 0.0001);
        }
    }



}
