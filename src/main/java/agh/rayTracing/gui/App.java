package agh.rayTracing.gui;

import agh.rayTracing.Engine;
import agh.rayTracing.hittable.HittableList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {

    HittableList objects = new HittableList();


    @Override
    public void start(Stage primaryStage){
        Label widthLabel = new Label("Output image width: ");
        TextField widthParam = new TextField("400");
        HBox widthBox = new HBox(widthLabel,widthParam);

        Label heightLabel = new Label("Output image height");
        TextField heightParam = new TextField("225");
        HBox heightBox = new HBox(heightLabel, heightParam);

        Label guiWidthLabel = new Label("gui widht: ");
        TextField guiWidthParam = new TextField("1280");
        HBox guiWidthBox = new HBox(guiWidthLabel,guiWidthParam);

        Label guiHeightLabel = new Label("gui height: ");
        TextField guiHeightParam = new TextField("720");
        HBox guiHeightBox = new HBox(guiHeightLabel, guiHeightParam);

        Label depthLabel = new Label("depth or recurrence");
        TextField depthParam = new TextField("10");
        HBox depthBox = new HBox(depthLabel, depthParam);

        Label densityLabel = new Label("density of rays per pixel");
        TextField densityParam = new TextField("10");
        HBox densityBox = new HBox(densityLabel, densityParam);

        Button addScene = new Button("Add object from file");
        Button readScene = new Button("Read scene from file");
        TextField addSceneField = new TextField("path to file");

        HBox addSceneBox = new HBox(addScene, readScene, addSceneField);

        Label point = new Label("belowe write x y z of sphere centre or plane point");
        TextField pointx = new TextField("X value");
        TextField pointy = new TextField("Y value");
        TextField pointz = new TextField("Z value");
        HBox pointBox = new HBox(pointx, pointy, pointz);

        Label cirrad = new Label("Below write radius of circle");
        TextField cirradfi = new TextField("Here write radious of circle");

        Label vec = new Label("belowe write x y z normal vector of plane, or vector of object push");
        TextField vecx = new TextField("X value");
        TextField vecy = new TextField("Y value");
        TextField vecz = new TextField("Z value");
        HBox vecBox = new HBox(vecx, vecy, vecz);


        Button addObj = new Button("Add obj from file");
        Button addSphere = new Button("Add Sphere");
        Button addPlane = new Button("Add plane");
        HBox addHittableBox = new HBox(addPlane, addSphere, addObj);

        Button startVisualization = new Button("StartVisualization");

        VBox aggregate = new VBox(widthBox, heightBox, guiWidthBox, guiHeightBox, depthBox, densityBox, point, pointBox,
                cirrad, cirradfi, vec, vecBox,
                addSceneBox, addHittableBox, startVisualization);
        aggregate.setAlignment(Pos.CENTER);

        Scene initScreen = new Scene(aggregate);
        primaryStage.setScene(initScreen);
        primaryStage.show();

        startVisualization.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Engine eng = new Engine(Integer.parseInt(guiWidthParam.getText()),
                        Integer.parseInt(guiHeightParam.getText()),
                        Integer.parseInt(widthParam.getText()),
                        Integer.parseInt(heightParam.getText()),
                        Integer.parseInt(densityParam.getText()),
                        Integer.parseInt(depthParam.getText()));
                Thread th = new Thread(eng);
                th.start();

            }
        });

    }

}


