package agh.rayTracing.render;

import agh.rayTracing.Main;
import agh.rayTracing.math.Vec3d;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.acos;
import static java.lang.Math.atan2;

public class ImageSky implements ISky{
    int imgHeight;
    int imgWidth;

    BufferedImage img;

    public ImageSky(BufferedImage b) throws IOException {
        this.imgHeight = b.getHeight() - 1;
        this.imgWidth = b.getWidth() - 1;
        this.img = b;
    }

    @Override
    public Vec3d getColor(Ray r) {

        Vec3d dir = r.direction.unitVec();

        int x = (int) ((atan2(-dir.z, dir.x) + Main.pi)/(2*Main.pi) * imgWidth);
        int y = (int) (acos(-dir.y) / Main.pi * imgHeight);
        int clr = this.img.getRGB(imgWidth - x, imgHeight - y);
        int red =   (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue =   clr & 0x000000ff;

        return new Vec3d(red / 255.999, green / 255.999, blue/ 255.999);

    }
}
