package com.poolgame.Strategy;

import com.poolgame.Entities.Ball;
import com.poolgame.Entities.BallConfig;
import javafx.scene.Group;

public class RemoveStrategy implements BallStrategy{
    @Override
    public void handleBallInPocket(Group root, Ball ball, BallConfig ballConfig) {
        root.getChildren().remove(ball.getCircle());
    }
}
