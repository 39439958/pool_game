package com.poolgame.Builder;

import com.poolgame.Entities.Ball;
import com.poolgame.Entities.BallConfig;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class BallBuilder {
    private BallConfig ballConfig;

    public BallBuilder(BallConfig ballConfig) {
        this.ballConfig = ballConfig;
    }

    public Ball buildWhiteBall(Group root) {
        Ball ball = new Ball();
        ball.getCircle().setFill(Color.WHITE);
        ball.setPos(ballConfig.getWhiteBall());
        ball.setID(0);
        ball.setHP(1);
        root.getChildren().add(ball.getCircle());
        return ball;
    }

    public ArrayList<Ball> buildBlueBall(Group root) {
        ArrayList<Ball> balls = new ArrayList<>();
        int blueNum = ballConfig.getBlueBall().size();
        for(int i = 1; i <= blueNum; i++) {
            Ball ball = new Ball();
            ball.getCircle().setFill(Color.BLUE);
            ball.setPos(ballConfig.getBlueBall().get(i - 1));
            ball.setID(i);
            ball.setHP(2);
            balls.add(ball);
            root.getChildren().add(ball.getCircle());
        }
        return balls;
    }

    public ArrayList<Ball> buildRedBall(Group root) {
        ArrayList<Ball> balls = new ArrayList<>();
        int blueNum = ballConfig.getBlueBall().size();
        int redNum = ballConfig.getRedBall().size();
        for (int i = blueNum + 1; i <= blueNum + redNum; i++) {
            Ball ball = new Ball();
            ball.getCircle().setFill(Color.RED);
            ball.setPos(ballConfig.getRedBall().get(i - 1 - blueNum));
            ball.setID(i);
            ball.setHP(1);
            balls.add(ball);
            root.getChildren().add(ball.getCircle());
        }
        return balls;
    }

    public Line buildPool(Group root) {
        Line line = new Line();
        line.setStroke(Color.BLACK);
        line.setVisible(false);
        root.getChildren().add(line);
        return line;
    }
}
