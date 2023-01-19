package agh.rayTracing.gui;

import agh.rayTracing.math.Vec3d;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Image;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Visualizer {
    int pixelWidth;
    int pixelHeight;
    int rowsNo;
    int colsNo;
    int width;
    int height;

    Stage stage;

    WritableImage img;

    Group root;

    ImageView iv;

    Rectangle[][] pixels;

    public Visualizer(int width, int height, int pixelWidth, int pixelHeight){

        this.rowsNo = height / pixelHeight;
        this.colsNo = width / pixelWidth;
        this.width = width;
        this.height = height;


        pixels = new Rectangle[rowsNo][colsNo];


        img = new WritableImage(colsNo, rowsNo);
        iv = new ImageView(img);
        iv.setFitHeight(height);
        iv.setFitWidth(width);
        root = new Group(iv);
        root.setAutoSizeChildren(true);
        Scene scene = new Scene(root, width, height);


        GridPane mainGrid = new GridPane();
//        for (int x = 0; x < colsNo; x++){
//            mainGrid.getColumnConstraints().addAll(new ColumnConstraints(pixelWidth));
//            for (int y = 0; y < rowsNo; y++){
//
//                pixels[y][x] = new Rectangle(pixelWidth, pixelHeight);
//                mainGrid.add(pixels[y][x], x, y);
//                GridPane.setHalignment(pixels[y][x], HPos.CENTER);
//                GridPane.setValignment(pixels[y][x], VPos.CENTER);
//            }
//        }
//
//        for (int y = 0; y < rowsNo; y++){
//            mainGrid.getRowConstraints().addAll(new RowConstraints(pixelHeight));
//        }

//        Scene scene = new Scene(mainGrid);
        System.out.println("here");
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void paintPixel(int x, int y, Vec3d col){
        y = this.rowsNo - y - 1;
        img.getPixelWriter().setColor(x, y,Color.color(col.x, col.y, col.z));
//        ImageView iv = new ImageView(img);
        iv.setImage(img);

//        root.getChildren().addAll(iv);
//        System.out.println("here");

//        pixels[y][x].setFill(Color.color(col.x, col.y, col.z));
    }
}
