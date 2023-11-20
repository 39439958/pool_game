package com.poolgame.JSON;

import javafx.geometry.Point2D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParse {
    public static Point2D getPoint2D(JSONObject json, String key) {
        JSONObject pointJson = json.getJSONObject(key);
        double x = pointJson.getDouble("x");
        double y = pointJson.getDouble("y");
        return new Point2D(x, y);
    }

    public static ArrayList<Point2D> getPoint2DArray(JSONObject json, String key) {
        ArrayList<Point2D> vector2DArray = new ArrayList<>();
        JSONArray vectorJson = json.getJSONArray(key);
        for (int i = 0; i < vectorJson.length(); i++) {
            double x = vectorJson.getJSONObject(i).getDouble("x");
            double y = vectorJson.getJSONObject(i).getDouble("y");
            vector2DArray.add(new Point2D(x, y));
        }
        return vector2DArray;
    }
}
