package agh.rayTracing.gui;

import agh.rayTracing.Engine;
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


    @Override
    public void start(Stage primaryStage){
        Label widthLabel = new Label("Pixel widht: ");
        TextField widthParam = new TextField("20");
        HBox widthBox = new HBox(widthLabel,widthParam);

        Label heightLabel = new Label("Pixel height");
        TextField heightParam = new TextField("20");
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

        Button addObject = new Button("Add object from file");
        TextField addObjectField = new TextField("path to file");
        HBox addObjectBox = new HBox(addObject, addObjectField);

        Button addSphere = new Button("Add Sphere");
        TextField addSpherePos = new TextField("position of sphere");
        TextField addSphereRad = new TextField("Radious of sphere");
        HBox addSphereBox = new HBox(addSphere, addSpherePos, addSphereRad);

        Button addPlane = new Button("Add plane");
        TextField addPlanePos = new TextField("point of plane");
        TextField addPlaneVec = new TextField("normal vector of plane");
        HBox addPlaneBox = new HBox(addPlane, addPlanePos, addPlaneVec);

        Button startVisualization = new Button("StartVisualization");

        VBox aggregate = new VBox(widthBox, heightBox, guiWidthBox, guiHeightBox, depthBox, densityBox,
                addObjectBox, addSphereBox, addPlaneBox, startVisualization);
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
                        Integer.parseInt(heightParam.getText()));
                Thread th = new Thread(eng);
                th.start();

            }
        });

    }

}


