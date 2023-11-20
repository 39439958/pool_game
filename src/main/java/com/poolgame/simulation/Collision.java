package com.poolgame.simulation;

import com.poolgame.Entities.Ball;
import com.poolgame.Entities.Table;
import javafx.geometry.Point2D;
import javafx.scene.control.Tab;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

public class Collision {

    public static Pair<Point2D, Point2D> calculateBallCollision(Point2D positionA, Point2D velocityA, double massA, Point2D positionB, Point2D velocityB , double massB) {
        Point2D collisionVector = positionA.subtract(positionB);
        collisionVector = collisionVector.normalize();
        double vA = collisionVector.dotProduct(velocityA);
        double vB = collisionVector.dotProduct(velocityB);
        if (vB <= 0 && vA >= 0) {
            return new Pair<>(velocityA, velocityB);
        }
        double optimizedP = (2.0 * (vA - vB)) / (massA + massB);
        Point2D velAPrime = velocityA.subtract(collisionVector.multiply(optimizedP).multiply(massB));
        Point2D velBPrime = velocityB.add(collisionVector.multiply(optimizedP).multiply(massA));
        return new Pair<>(velAPrime, velBPrime);
    }

    public static Point2D calculateEdgeCollision(Ball ball, Table table) {
        Circle circle = ball.getCircle();
        Point2D v = ball.getVelocity();
        double r = circle.getRadius();
        Point2D pos = ball.getPos();
        if (circle.getCenterX() - r < 0) {
            v = new Point2D(-ball.getVelocity().getX(), ball.getVelocity().getY());
            ball.setPos(new Point2D(r, pos.getY()));
        }
        if (circle.getCenterX() + r > table.getSize().getX()) {
            v = new Point2D(-ball.getVelocity().getX(), ball.getVelocity().getY());
            ball.setPos(new Point2D(table.getSize().getX() - r, pos.getY()));
        }
        if (circle.getCenterY() - r < 0) {
            v = new Point2D(ball.getVelocity().getX(), -ball.getVelocity().getY());
            ball.setPos(new Point2D(pos.getX(), r));
        }
        if (circle.getCenterY() + r > table.getSize().getY()) {
            v = new Point2D(ball.getVelocity().getX(), -ball.getVelocity().getY());
            ball.setPos(new Point2D(pos.getX(), table.getSize().getY() - r));
        }
        return v;
    }

}
