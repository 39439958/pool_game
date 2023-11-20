package com.poolgame.simulation;

import com.poolgame.Entities.Ball;
import com.poolgame.Entities.Table;
import javafx.geometry.Point2D;

public class Friction {

    public static Point2D calculateVelocity(Ball ball, double friction) {
        Point2D velocity = ball.getVelocity();
        double v = ball.calculateVelocity();
        if(v > 0.05) {
            double unitX = velocity.getX() / v;
            double unitY = velocity.getY() / v;
            velocity = velocity.subtract(friction * unitX, friction * unitY);
        }
        else {
            velocity = velocity.subtract(velocity.getX(), velocity.getY());
        }
        return velocity;
    }
}
