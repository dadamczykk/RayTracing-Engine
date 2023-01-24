package agh.rayTracing.math;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Vec3dTest {

    @Test
    public void oppositeTest(){
            Random rand = new Random();
            for (int i = 0; i < 10; i++){
                int x = rand.nextInt();
                int y = rand.nextInt();
                int z = rand.nextInt();
                Vec3d v1 = new Vec3d(x,y,z);
                Vec3d v2 = new Vec3d(-x,-y,-z);
                assertEquals(v1.opposite(), v2);
            }
    }

//    @Test
//    public void subtractTest(){
//
//    }

}
