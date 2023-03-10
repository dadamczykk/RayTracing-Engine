package agh.rayTracing.gui;

import agh.rayTracing.hittable.*;
import agh.rayTracing.materials.*;
import agh.rayTracing.math.Vec3d;
import agh.rayTracing.render.BasicSky;
import agh.rayTracing.render.Engine;
import agh.rayTracing.render.ISky;
import agh.rayTracing.render.ImageSky;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class App extends Application {

    ISky sky = new BasicSky(new Vec3d(0.7,0.3,0.9));
    Thread rendering;

    @Override
    public void start(Stage primaryStage){
        Label widthLabel = new Label("Output image width:     ");
        TextField widthParam = new TextField("400");
        HBox widthBox = new HBox(widthLabel,widthParam);

        Label heightLabel = new Label("Output image height:     ");
        TextField heightParam = new TextField("225");
        HBox heightBox = new HBox(heightLabel, heightParam);

        Label guiWidthLabel = new Label("Render screen width:     ");
        TextField guiWidthParam = new TextField("1280");
        HBox guiWidthBox = new HBox(guiWidthLabel,guiWidthParam);

        Label guiHeightLabel = new Label("Render screen height:     ");
        TextField guiHeightParam = new TextField("720");
        HBox guiHeightBox = new HBox(guiHeightLabel, guiHeightParam);

        Label depthLabel = new Label("Depth or recurrence:     ");
        TextField depthParam = new TextField("10");
        HBox depthBox = new HBox(depthLabel, depthParam);

        Label densityLabel = new Label("Density of rays per pixel:     ");
        TextField densityParam = new TextField("10");
        HBox densityBox = new HBox(densityLabel, densityParam);

        Button addScene = new Button("Save scene to file");
        Button readScene = new Button("Read scene from file");
        TextField addSceneField = new TextField("Path to file");

        HBox addSceneBox = new HBox(addScene, readScene, addSceneField);

        Label cameraStart = new Label("Below write x, y, z coordinates of point where camera stands");
        TextField camStartx = new TextField("-4");
        TextField camStarty = new TextField("2");
        TextField camStartz = new TextField("-2");
        HBox camStartBox = new HBox(camStartx, camStarty, camStartz);

        Label cameraEnd = new Label("Below write x, y, z coordinates of point where camera is focused");
        TextField camEndx = new TextField("0");
        TextField camEndy = new TextField("1");
        TextField camEndz = new TextField("-1");
        HBox camEndBox = new HBox(camEndx, camEndy, camEndz);

        Label point = new Label("Below write x, y, z coordinates of sphere centre, or point contained in plane");
        TextField pointx = new TextField("x value");
        TextField pointy = new TextField("y value");
        TextField pointz = new TextField("z value");
        HBox pointBox = new HBox(pointx, pointy, pointz);

        Label cirrad = new Label("Below write radius of sphere");
        TextField cirradfi = new TextField("Radious");

        Label vec = new Label("Below write x, y, z coordinates of normal vector of plane," +
                "or vector of object shift");
        TextField vecx = new TextField("x value");
        TextField vecy = new TextField("y value");
        TextField vecz = new TextField("z value");
        HBox vecBox = new HBox(vecx, vecy, vecz);

        Label col = new Label("Below write color of objects material, or background in RGB values");
        TextField colR = new TextField("Red value");
        TextField colG = new TextField("Green value");
        TextField colB = new TextField("Blue value");
        HBox colBox = new HBox(colR, colG, colB);

        Button addObj = new Button("Add obj from file");
        Button addSphere = new Button("Add Sphere");
        Button addPlane = new Button("Add plane");
        Button setBGcol = new Button("Set background color");
        Button setBGImg = new Button("Set background image");
        HBox addHittableBox = new HBox(addPlane, addSphere, addObj, setBGcol, setBGImg);

        Label difref = new Label("Below write diffusion (for metal material), or refraction (for glass) value");
        TextField dif = new TextField("Diffusion, values between [0,1]");
        TextField ref = new TextField("Refraction, for example 1.5");
        HBox difrefbox = new HBox(dif, ref);

        ObservableList<MaterialType> materials = FXCollections.observableArrayList(MaterialType.MAT,
                MaterialType.GLASS, MaterialType.LIGHT, MaterialType.METAL);
        ComboBox<MaterialType> materialsCombo = new ComboBox<>(materials);


        Button startVisualization = new Button("Start rendering");

        Label info = new Label("");

        VBox aggregate = new VBox(widthBox, heightBox, guiWidthBox, guiHeightBox, depthBox, densityBox,
                cameraStart, camStartBox, cameraEnd, camEndBox, materialsCombo,
                col, colBox, point, pointBox, difref, difrefbox,
                cirrad, cirradfi, vec, vecBox,
                addSceneBox, addHittableBox, startVisualization, info);
        aggregate.setAlignment(Pos.CENTER);

        Scene initScreen = new Scene(aggregate);
        primaryStage.setScene(initScreen);
        primaryStage.show();


        HittableList hittables = new HittableList();

        App app = this;



        addPlane.setOnAction(event -> {
            try{
                AbstractMaterial material = getMaterial(colR.getText(), colG.getText(), colB.getText(),
                        materialsCombo.getValue(), dif.getText(), ref.getText());

                Vec3d pos = new Vec3d(Double.parseDouble(pointx.getText()), Double.parseDouble(pointy.getText()),
                        Double.parseDouble(pointz.getText()));

                Vec3d normal = new Vec3d(Double.parseDouble(vecx.getText()), Double.parseDouble(vecy.getText()),
                        Double.parseDouble(vecz.getText()));

                hittables.add(new Plane(pos, normal, material));
                info.setText("Plane has been added.");
            } catch (Exception e){
                info.setText(e.getMessage());
            }

        });


        addSphere.setOnAction(event -> {
            try {
                AbstractMaterial material = getMaterial(colR.getText(), colG.getText(), colB.getText(),
                        materialsCombo.getValue(), dif.getText(), ref.getText());

                Vec3d pos = new Vec3d(Double.parseDouble(pointx.getText()), Double.parseDouble(pointy.getText()),
                        Double.parseDouble(pointz.getText()));
                hittables.add(new Sphere(pos, Double.parseDouble(cirradfi.getText()), material));
                info.setText("Sphere has been added.");
            } catch (Exception ee) {
                info.setText(ee.getMessage());
            }
        });



        setBGcol.setOnAction(event -> {
            try {
                Vec3d col1 = new Vec3d(Double.parseDouble(colR.getText()),
                        Double.parseDouble(colG.getText()), Double.parseDouble(colB.getText()));
                if (col1.x < 0 || col1.x > 255 || col1.y < 0 || col1.y > 255 || col1.z < 0 || col1.z > 255) {
                    throw new RuntimeException("Wrong color values, sould be from interval [0,255]");
                }
                col1.divideSelf(255.999);
                setISkyCol(col1);
                info.setText("Background color has been set.");

            } catch (Exception e) {

                info.setText("Wrong color values");
            }
        });

        setBGImg.setOnAction(event -> {
            try{
                File file = new File(addSceneField.getText());
                BufferedImage img = ImageIO.read(file);
                setISkyImg(img);
                info.setText("Background image has been set.");
            } catch (IOException e) {
                info.setText(e.getMessage());
            }
        });

        addScene.setOnAction(event -> {
            try {
                String filename = addSceneField.getText();

                saveSceneToFile(hittables, filename);
                info.setText("Scene successfully saved to file.");
            }catch (Exception e){
                info.setText(e.getMessage());
            }

        });


        readScene.setOnAction(event -> {
            try{
                readSceneFromFile(hittables, addSceneField.getText());
                info.setText("Scene successfully read from file");
            } catch (Exception e){
                info.setText(e.getMessage());
            }
        });

        addObj.setOnAction(event -> {
            try{
                Vec3d push = new Vec3d(Double.parseDouble(vecx.getText()),
                        Double.parseDouble(vecy.getText()),
                        Double.parseDouble(vecz.getText()));
                AbstractMaterial material = getMaterial(colR.getText(), colG.getText(), colB.getText(),
                        materialsCombo.getValue(), dif.getText(), ref.getText());
                readObjFile(hittables, addSceneField.getText(), material, push);
                info.setText("Object has been added");
            } catch (Exception e){
                info.setText(e.getMessage());
            }
        });




        startVisualization.setOnAction(event -> {
            try {
                if ( Integer.parseInt(guiWidthParam.getText()) < 1 ||
                        Integer.parseInt(guiHeightParam.getText()) < 1 ||
                        Integer.parseInt(widthParam.getText()) <1 ||
                        Integer.parseInt(heightParam.getText()) < 1||
                        Integer.parseInt(densityParam.getText())< 1||
                        Integer.parseInt(depthParam.getText()) < 1){
                        throw new RuntimeException("nonpositive");
                }
                Engine eng = new Engine(Integer.parseInt(guiWidthParam.getText()),
                        Integer.parseInt(guiHeightParam.getText()),
                        Integer.parseInt(widthParam.getText()),
                        Integer.parseInt(heightParam.getText()),
                        Integer.parseInt(densityParam.getText()),
                        Integer.parseInt(depthParam.getText()), hittables, sky, app,
                        new Vec3d(Double.parseDouble(camStartx.getText()),
                        Double.parseDouble(camStarty.getText()),
                        Double.parseDouble(camStartz.getText())),
                        new Vec3d(Double.parseDouble(camEndx.getText()),
                                Double.parseDouble(camEndy.getText()),
                                Double.parseDouble(camEndz.getText())));
                Thread th = new Thread(eng);
                th.start();
                setThread(th);
                info.setText("Rendering has started.");
            } catch (Exception e){
                info.setText("Wrong value(s) of width/height/depth/density/camera position.");
            }


        });

    }

    private void setISkyCol(Vec3d col){
        this.sky = new BasicSky(col);
    }

    private void setISkyImg(BufferedImage img) throws IOException {
        this.sky = new ImageSky(img);
    }

    private void setThread(Thread th){
        rendering = th;
    }


    private AbstractMaterial getMaterial(String R, String G, String B, MaterialType mt, String diff, String  refr){

        if (mt == null){
            throw new RuntimeException("You have to choose material type");
        }

        switch (mt){
            case MAT -> {
                Vec3d col = new Vec3d(Double.parseDouble(R), Double.parseDouble(G), Double.parseDouble(B));
                if (col.x < 0 || col.x > 255 || col.y < 0 || col.y > 255 || col.z < 0 || col.z > 255){
                    throw new RuntimeException("Wrong color values, sould be from interval [0,255]");
                }
                col.divideSelf(255.999);
                return new Lambertian(col);
            }
            case LIGHT -> {
                return new Light(new Vec3d(1,1,1));
            }
            case METAL -> {
                Vec3d col = new Vec3d(Double.parseDouble(R), Double.parseDouble(G), Double.parseDouble(B));
                if (col.x < 0 || col.x > 255 || col.y < 0 || col.y > 255 || col.z < 0 || col.z > 255){
                    throw new RuntimeException("Wrong color values, sould be from interval [0,255]");
                }
                col.divideSelf(255.999);
                double diffval = Double.parseDouble(diff);
                if (diffval < 0 || diffval > 1){
                    throw new RuntimeException("wrong diffuse value, should be in interval [0,1]");
                }
                return new Metal(col, diffval);
            }
            case GLASS -> {
                double refractVal = Double.parseDouble(refr);
                if (refractVal < 0){
                    throw new RuntimeException("Refract value should be positive");
                }
                return new Glass(refractVal);
            }
        }
        return new Light(new Vec3d(1,1,1));
    }


    private void readObjFile(HittableList hittables, String filename, AbstractMaterial material, Vec3d push)
            throws FileNotFoundException {
        if (!filename.matches(".*\\.obj$")){
            filename += ".obj";
        }
        File f = new File(filename);
        try (Scanner scan = new Scanner(f)){
            ArrayList<Vec3d> vertices = new ArrayList<>();
            vertices.add(new Vec3d(0,0,0));
            while (scan.hasNextLine()){

                String toParse = scan.nextLine();
                String[] parts = toParse.split(" ");
                List<String> list = new ArrayList<>(Arrays.asList(parts));
                list.removeAll(Arrays.asList("", null));
                if (list.size() < 1){
                    continue;
                }
                if (list.get(0).equals("v")){
                    vertices.add(new Vec3d(Double.parseDouble(list.get(1)),
                            Double.parseDouble(list.get(2)),
                            Double.parseDouble(list.get(3))).add(push));
                } else if (parts[0].equals("f")){
                    hittables.add(new Triangle(vertices.get(Integer.parseInt(list.get(1))),
                            vertices.get(Integer.parseInt(list.get(2))),
                            vertices.get(Integer.parseInt(list.get(3))), material));
                }
            }
        }
    }

    private void readSceneFromFile(HittableList objects, String filename) throws IOException{
        if (!filename.matches(".*\\.txt$")){
            filename += ".txt";
        }
        File nf = new File(filename);
        try (Scanner scan = new Scanner(nf)) {
            while (scan.hasNextLine()){
                String toParse = scan.nextLine();
                String[] parts = toParse.split(",");
                int idx;
                AbstractMaterial material;
                idx = switch (parts[0]) {
                    case "sphere" -> 5;
                    case "plane" -> 7;
                    case "triangle" -> 10;
                    default -> throw new RuntimeException("Corrupted file");
                };
                material = switch (parts[idx]) {
                    case "light" -> new Light(new Vec3d(1, 1, 1));
                    case "glass" -> new Glass(Double.parseDouble(parts[idx + 1]));
                    case "metal" -> new Metal(new Vec3d(
                            Double.parseDouble(parts[idx + 1]),
                            Double.parseDouble(parts[idx + 2]),
                            Double.parseDouble(parts[idx + 3])
                    ), Double.parseDouble(parts[idx + 4]));
                    default -> throw new RuntimeException("Corrupted file");
                };
                if (idx == 5){
                    objects.add(new Sphere(new Vec3d(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])),
                            Double.parseDouble(parts[4]), material));
                } else if( idx == 7){
                    objects.add(new Plane(new Vec3d(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])), new Vec3d(
                            Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5]),
                            Double.parseDouble(parts[6])), material));
                } else {
                    objects.add(new Triangle(new Vec3d(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])), new Vec3d(
                            Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5]),
                            Double.parseDouble(parts[6])), new Vec3d(
                            Double.parseDouble(parts[7]),
                            Double.parseDouble(parts[8]),
                            Double.parseDouble(parts[9])), material));
                }

            }
        }
    }

    private void saveSceneToFile(HittableList objects, String filename) throws IOException {
        if (!filename.matches(".*\\.txt$")){
            filename += ".txt";
        }
        File newFile = new File(filename);
        if (!newFile.createNewFile()){
            throw new RuntimeException("File with that name already exists");
        }
        FileWriter myWriter = new FileWriter(filename);

        for (AbstractHittable obj : objects.objects){
            if (obj instanceof Sphere s){
                myWriter.write("sphere,");
                myWriter.write(s.center.x + "," + s.center.y + "," + s.center.z + "," + s.radious + ",");
            }
            if (obj instanceof Plane p){
                myWriter.write("plane,");
                myWriter.write(p.point.x + "," + p.point.y + "," + p.point.z + "," +
                        p.normal.x + "," + p.normal.y + "," + p.normal.z + ",");
            }
            if (obj instanceof Triangle p){
                myWriter.write("triangle,");
                myWriter.write(p.a.x + "," + p.a.y + "," + p.a.z + "," +
                        p.b.x + "," + p.b.y + "," + p.b.z + "," +
                        p.c.x + "," + p.c.y + "," + p.c.z + ",");
            }
            if (obj.material instanceof Light){
                myWriter.write("light,");
            }
            if (obj.material instanceof Glass){
                myWriter.write("glass," + ((Glass) obj.material).refractionInd);
            }
            if (obj.material instanceof Metal m){
                myWriter.write("metal,");
                myWriter.write(m.col.x + "," + m.col.y + "," + m.col.z + "," +m.scatter);
            }
            myWriter.write("\n");
        }
        myWriter.close();
    }


}




