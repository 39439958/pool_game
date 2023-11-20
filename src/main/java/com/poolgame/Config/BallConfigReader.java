package com.poolgame.Config;

import com.poolgame.Entities.BallConfig;
import com.poolgame.JSON.JsonParse;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BallConfigReader implements ConfigReader{

    @Override
    public BallConfig readConfig(String configPath) {
        BallConfig ballconfig = new BallConfig();
        try{
            String content = new String(Files.readAllBytes(Paths.get(configPath)));
            JSONObject json = new JSONObject(content);
            ballconfig.setWhiteBall(JsonParse.getPoint2D(json,"whiteBall"));
            ballconfig.setBlueBall(JsonParse.getPoint2DArray(json,"blueBall"));
            ballconfig.setRedBall(JsonParse.getPoint2DArray(json,"redBall"));
            ballconfig.setMass(json.getDouble("mass"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ballconfig;
    }
}
