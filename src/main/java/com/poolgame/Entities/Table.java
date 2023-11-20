package com.poolgame.Entities;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Table {
    private double friction;
    private ArrayList<Circle> pockets;
    private Canvas canvas;
    private TableConfig tableConfig;

    public Table(TableConfig tableConfig) {
        canvas = new Canvas();
        pockets = new ArrayList<>();
        this.tableConfig = tableConfig;
    }

    public void initialTable(Group root) {
        // set table's friction
        this.friction = tableConfig.getFriction();
        // set table's size
        Point2D size = tableConfig.getSize();
        canvas.setWidth(size.getX());
        canvas.setHeight(size.getY());
        // set table's color
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(tableConfig.getColor());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        root.getChildren().add(canvas);
        // set pockets
        double width = size.getX();
        double height = size.getY();
        double holeRadius = 40;
        double offset = 10;
        Point2D pocketPos[] = {
                new Point2D(offset, offset),
                new Point2D(offset, height - offset),
                new Point2D(width/2, offset),
                new Point2D(width/2, height-offset),
                new Point2D(width, offset),
                new Point2D(width, height-offset)
        };
        for(int i=0; i<6; i++) {
            Point2D pos = pocketPos[i];
            Circle hole = new Circle(pos.getX(), pos.getY(), holeRadius);
            pockets.add(hole);
            root.getChildren().add(hole);
        }
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public ArrayList<Circle> getPockets() {
        return pockets;
    }

    public void setPockets(ArrayList<Circle> pockets) {
        this.pockets = pockets;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }

    public Point2D getSize() {
        return new Point2D(canvas.getWidth(), canvas.getHeight());
    }

    public void setSize(Point2D size) {
        canvas.setWidth(size.getX());
        canvas.setHeight(size.getY());
    }

}
