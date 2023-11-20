package com.poolgame.Entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {
    private Circle circle;
    private Point2D velocity;
    private int HP;
    private int ID;

    public Ball() {
        circle = new Circle(0, 0, 20);
        velocity = new Point2D(0, 0);
        HP = 0;
    }

    public void update() {
        setPos(getPos().add(velocity));
    }
    public double calculateVelocity() {
        double v = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getY() * velocity.getY());
        return v;
    }
    public double getDistance(Ball ball) {
        return Math.sqrt(Math.pow(ball.getPos().getX() - getPos().getX(), 2) + Math.pow(ball.getPos().getY() - getPos().getY(), 2));
    }
    public Point2D getPos() {
        return new Point2D(circle.getCenterX(), circle.getCenterY());
    }
    public void setPos(Point2D pos) {
        circle.setCenterX(pos.getX());
        circle.setCenterY(pos.getY());
    }

    public Circle getCircle() {
        return circle;
    }
    public Point2D getVelocity() {
        return velocity;
    }
    public int getHP() {
        return HP;
    }
    public void setCircle(Circle circle) {
        this.circle = circle;
    }
    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
}
