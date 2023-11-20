package com.poolgame.Strategy;

import com.poolgame.Entities.Ball;
import com.poolgame.Entities.BallConfig;
import javafx.scene.Group;

public interface BallStrategy {
    public void handleBallInPocket(Group root, Ball ball, BallConfig ballConfig);
}
