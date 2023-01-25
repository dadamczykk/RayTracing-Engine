package agh.rayTracing.gui;

import agh.rayTracing.math.Vec3d;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Visualizer {
    int rowsNo;
    int colsNo;
    int width;
    int height;

    Stage stage;

    WritableImage img;

    Label precentage = new Label("0%");

    Group root;

    ImageView iv;

    Rectangle[][] pixels;

    Scene scene;

    GridPane mainGrid;


    public Visualizer(int guiWidth, int guiHeight, int outWidth, int outHeight, App init){

        this.rowsNo = outHeight;
        this.colsNo = outWidth;
        this.width = guiWidth;
        this.height = guiHeight;


        pixels = new Rectangle[rowsNo][colsNo];

        img = new WritableImage(colsNo, rowsNo);
        iv = new ImageView(img);
        iv.setFitHeight(height);
        iv.setFitWidth(width);
        root = new Group(iv, precentage);
        root.setAutoSizeChildren(true);
        mainGrid = new GridPane();
        mainGrid.add(iv, 0, 0);
        scene = new Scene(mainGrid, width, height);




        stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            init.rendering.interrupt();
        });
    }

    public void writePixel(int x, int y, Vec3d col) {
        y = this.rowsNo - y - 1;
        img.getPixelWriter().setColor(x, y, Color.color(col.x, col.y, col.z));
    }

    }
