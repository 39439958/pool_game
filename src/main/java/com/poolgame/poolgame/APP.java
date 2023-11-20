package com.poolgame.poolgame;

import com.poolgame.Builder.BallBuilder;
import com.poolgame.Config.ConfigReader;
import com.poolgame.Config.ConfigReaderFactory;
import com.poolgame.Entities.Ball;
import com.poolgame.Entities.BallConfig;
import com.poolgame.Entities.Table;
import com.poolgame.Entities.TableConfig;
import com.poolgame.Strategy.RemoveStrategy;
import com.poolgame.Strategy.ResetStrategy;
import com.poolgame.simulation.Collision;
import com.poolgame.simulation.Friction;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;

public class APP extends Application {
    private Group root;
    private Scene scene;
    private TableConfig tableConfig;
    private BallConfig ballConfig;
    private Ball whiteBall;
    private Line poolLine;
    private ArrayList<Ball> blueBalls;
    private ArrayList<Ball> redBalls;
    private ArrayList<Ball> allBalls;
    private static final double KEY_FRAME_DURATION = 1.0 / 120.0;
    private static final double BALL_SPEED = 0.2;
    private Table table;
    private double mouseX;
    private double mouseY;
    private Ball selectedBall;
    private boolean isDragging;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        scene = new Scene(root);

        // set handle
        scene.setOnMousePressed(this::handleMousePressed);
        scene.setOnMouseDragged(this::handleMouseDragged);
        scene.setOnMouseReleased(this::handleMouseReleased);

        // set stage
        primaryStage.setWidth(1016);
        primaryStage.setHeight(536);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PollGame");
        primaryStage.show();

        // load configuration
        ConfigReaderFactory factory = new ConfigReaderFactory();
        ConfigReader ballConfigReader = factory.createConfigReader("ball");
        ConfigReader tableConfigReader = factory.createConfigReader("table");
        tableConfig = (TableConfig) tableConfigReader.readConfig("C:\\Users\\xuliz\\IdeaProjects\\PoolGame\\src\\main\\resources\\tableConfig.json");
        ballConfig = (BallConfig) ballConfigReader.readConfig("C:\\Users\\xuliz\\IdeaProjects\\PoolGame\\src\\main\\resources\\ballConfig.json");

        // create table
        table = new Table(tableConfig);
        table.initialTable(root);

        // create balls and pool
        BallBuilder ballBuilder = new BallBuilder(ballConfig);
        whiteBall = ballBuilder.buildWhiteBall(root);
        blueBalls = ballBuilder.buildBlueBall(root);
        redBalls = ballBuilder.buildRedBall(root);
        poolLine = ballBuilder.buildPool(root);
        allBalls = new ArrayList<Ball>();
        allBalls.add(whiteBall);
        allBalls.addAll(blueBalls);
        allBalls.addAll(redBalls);

        // start game
        playGame();
    }

    public void playGame() {
        Timeline animationLoop = new Timeline();
        animationLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(KEY_FRAME_DURATION), (actionEvent) -> updateGame());
        animationLoop.getKeyFrames().add(frame);
        animationLoop.play();
    }

    public void updateGame() {
        updateBalls();
        handleCollision();
        handleBallInPocket();
    }

    private void updateBalls() {
        double friction = table.getFriction();
        ArrayList<Circle> holes = table.getPockets();
        for (Ball ball : allBalls) {
            ball.setVelocity(Friction.calculateVelocity(ball, friction));
            ball.update();
        }
    }

    public void handleCollision() {
        for(int i = 0; i < allBalls.size(); i++) {
            for(int j = i + 1; j < allBalls.size(); j++) {
                if(checkBallCollision(allBalls.get(i), allBalls.get(j))) {
                    Ball ball1 = allBalls.get(i);
                    Ball ball2 = allBalls.get(j);
                    Pair<Point2D, Point2D> result = Collision.calculateBallCollision(
                            ball1.getPos(),
                            ball1.getVelocity(),
                            ballConfig.getMass(),
                            ball2.getPos(),
                            ball2.getVelocity(),
                            ballConfig.getMass()
                    );
                    allBalls.get(i).setVelocity(result.getKey());
                    allBalls.get(j).setVelocity(result.getValue());
                }
            }
        }
        for (Ball ball : allBalls) {
            Point2D v = Collision.calculateEdgeCollision(ball, table);
            ball.setVelocity(v);
        }
    }

    public void handleBallInPocket() {
        ArrayList<Circle> holes = table.getPockets();
        int blueNum = ballConfig.getBlueBall().size();
        int redNum = ballConfig.getRedBall().size();
        for(int i = 0; i < allBalls.size(); i++) {
            Ball ball = allBalls.get(i);
            if(checkBallInPocket(ball)) {
                if(ball.getID() == 0) {
                    if(ball.getHP() > 0) {
                        ResetStrategy resetBallStrategy = new ResetStrategy();
                        resetBallStrategy.handleBallInPocket(root, ball, ballConfig);
                    }
                    else {
                        RemoveStrategy removeStrategy = new RemoveStrategy();
                        removeStrategy.handleBallInPocket(root, ball, ballConfig);
                        showText("You Lose!");
                    }

                }
                else if(ball.getID() >= 1 + blueNum && ball.getID() <= blueNum+redNum) {
                    RemoveStrategy removeStrategy = new RemoveStrategy();
                    removeStrategy.handleBallInPocket(root, ball, ballConfig);
                    allBalls.remove(ball);
                    if(checkWin()) {
                        showText("You Win!");
                    }
                }
                else if(ball.getID() >= 1 && ball.getID() <= blueNum) {
                    if(ball.getHP() > 0) {
                        ResetStrategy resetStrategy = new ResetStrategy();
                        resetStrategy.handleBallInPocket(root, ball, ballConfig);
                    }
                    else {
                        RemoveStrategy removeStrategy = new RemoveStrategy();
                        removeStrategy.handleBallInPocket(root, ball, ballConfig);
                        allBalls.remove(ball);
                        if(checkWin()) {
                            showText("You Win!");
                        }
                    }

                }
            }
        }
    }

    public boolean checkBallCollision(Ball ballA, Ball ballB) {
        double distance = ballA.getDistance(ballB);
        return distance <= ballA.getCircle().getRadius() + ballB.getCircle().getRadius();
    }

    public boolean checkBallInPocket(Ball ball) {
        ArrayList<Circle> pockets = table.getPockets();
        for(Circle pocket : pockets) {
            Ball tmpBall = new Ball();
            tmpBall.setCircle(pocket);
            if(tmpBall.getCircle().contains(ball.getPos())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWin() {
        return allBalls.size() == 1 && allBalls.get(0).getID() == 0;
    }

    public void showText(String s) {
        Text text = new Text(s);
        text.setFont(new Font(30));
        text.setX(400);
        text.setY(200);
        root.getChildren().add(text);
    }

    private void handleMousePressed(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        Ball ball = whiteBall;
        if (ball.getCircle().contains(new Point2D(mouseX, mouseY)) && ball.getVelocity().equals(new Point2D(0, 0))) {
            mouseX = ball.getPos().getX();
            mouseY = ball.getPos().getY();
            selectedBall = ball;
            isDragging = true;
        }
    }
    private void handleMouseDragged(MouseEvent event) {
        if (isDragging && selectedBall != null) {
            double tmpX = event.getX();
            double tmpY = event.getY();
            poolLine.setVisible(true);
            poolLine.setStartX(whiteBall.getPos().getX());
            poolLine.setStartY(whiteBall.getPos().getY());
            poolLine.setEndX(tmpX);
            poolLine.setEndY(tmpY);
        }
    }
    private void handleMouseReleased(MouseEvent event) {
        if (isDragging && selectedBall != null) {
            poolLine.setVisible(false);
            double deltaX =  mouseX - event.getX();
            double deltaY = mouseY - event.getY();
            double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY) * BALL_SPEED;
            double angle = Math.atan2(deltaY, deltaX);
            selectedBall.setVelocity(new Point2D(speed * Math.cos(angle), speed * Math.sin(angle)));
            isDragging = false;
            selectedBall = null;
        }
    }

}