package com.poolgame.Config;

import com.poolgame.Entities.TableConfig;

import com.poolgame.JSON.JsonParse;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TableConfigReader implements ConfigReader{

    @Override
    public TableConfig readConfig(String configPath) {
        TableConfig tableConfig = new TableConfig();
        try{
            String content = new String(Files.readAllBytes(Paths.get(configPath)));
            JSONObject json = new JSONObject(content);
            tableConfig.setFriction(json.getDouble("friction"));
            tableConfig.setSize(JsonParse.getPoint2D(json,"size"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableConfig;
    }
}
