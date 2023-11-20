package com.poolgame.Entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class TableConfig {
    private Point2D size;
    private double friction;

    private Color color = Color.GREEN;

    public Point2D getSize() {
        return size;
    }

    public void setSize(Point2D size) {
        this.size = size;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public Color getColor() {
        return color;
    }
}
