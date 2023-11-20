package com.poolgame.Entities;

import javafx.geometry.Point2D;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BallConfig {
    private Point2D whiteBall;
    private ArrayList<Point2D> redBall;
    private ArrayList<Point2D> blueBall;
    double mass;

    public Point2D getWhiteBall() {
        return whiteBall;
    }

    public void setWhiteBall(Point2D whiteBall) {
        this.whiteBall = whiteBall;
    }

    public ArrayList<Point2D> getRedBall() {
        return redBall;
    }

    public void setRedBall(ArrayList<Point2D> redBall) {
        this.redBall = redBall;
    }

    public ArrayList<Point2D> getBlueBall() {
        return blueBall;
    }

    public void setBlueBall(ArrayList<Point2D> blueBall) {
        this.blueBall = blueBall;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}
