package agh.rayTracing;

import agh.rayTracing.gui.App;
import javafx.application.Application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Main {

    public static double randomDouble(){
        Random r = new Random();
        return r.nextDouble();
    }

    public static double randomDouble(double min, double max){
        return min + (max - min) * randomDouble();
    }

    public static double pi = 3.14159265358;
    public static double inf = Double.MAX_VALUE;

    public static double degToRad(double deg){
        return deg * pi / 180;
    }

    public static void main(String[] args) throws IOException {
        Application.launch(App.class);

    }
}