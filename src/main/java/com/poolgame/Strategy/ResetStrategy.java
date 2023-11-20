package com.poolgame.Strategy;

import com.poolgame.Entities.Ball;
import com.poolgame.Entities.BallConfig;
import javafx.geometry.Point2D;
import javafx.scene.Group;

public class ResetStrategy implements BallStrategy{
    @Override
    public void handleBallInPocket(Group root, Ball ball, BallConfig ballConfig) {
        int id = ball.getID();
        if (id == 0) {
            ball.setPos(ballConfig.getWhiteBall());
            ball.setVelocity(new Point2D(0, 0));
            ball.setHP(ball.getHP()-1);
        }
        else {
            Point2D pos = ballConfig.getBlueBall().get(id - 1);
            ball.setPos(pos);
            ball.setVelocity(new Point2D(0, 0));
            ball.setHP(ball.getHP()-1);
        }
    }
}
