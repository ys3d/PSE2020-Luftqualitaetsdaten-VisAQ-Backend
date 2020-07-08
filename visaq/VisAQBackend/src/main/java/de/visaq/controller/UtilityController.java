package de.visaq.controller;

import java.awt.Point;
import java.time.Instant;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import de.visaq.model.sensorthings.UnitOfMeasurement;

/**
 * Implements some utility functions that allow for easier model construction.
 */
public final class UtilityController {

    private UtilityController() {
    }

    /**
     * Constructs a UnitOfMeasurement instance from a JSON Object.
     * 
     * @param json The JSON Object.
     * @return The constructed Point.
     */
    public static UnitOfMeasurement buildUnitOfMeasurement(JSONObject json) {
        return new UnitOfMeasurement(json.getString("name"), json.getString("symbol"),
                json.getString("definition"));
    }

    /**
     * Constructs an Instant instance from a JSON Object Key-Value pair.
     * 
     * @param json The JSON Object.
     * @param key  The key that corresponds to the value containing the Instant codified.
     * @return The constructed Instance.
     */
    public static Instant buildTime(JSONObject json, String key) {
        return Instant.parse(json.getString(key));
    }

    /**
     * Constructs a Point instance from a JSON Object by parsing the location and feature key.
     * 
     * @param json The JSON Object.
     * @return The constructed Point.
     */
    public static Point buildLocationPoint(JSONObject json) {
        JSONArray pointArray;

        if (json.has("location")) {
            pointArray = json.getJSONObject("location").getJSONArray("coordinates");
        } else if (json.has("feature")) {
            pointArray = json.getJSONObject("feature").getJSONArray("coordinates");
        } else {
            return null;
        }

        Point point = new Point();
        point.setLocation(pointArray.getDouble(0), pointArray.getDouble(1));
        return point;
    }

    /**
     * Constructs the properties Map from a JSON Object.
     * 
     * @param json The JSON Object.
     * @return The constructed Map.
     */
    public static Map<String, Object> buildProperties(JSONObject json) {
        if (!json.has("properties")) {
            return null;
        }

        return json.getJSONObject("properties").toMap();
    }

    /**
     * Removes the array wrapper around some single value arrays.
     * 
     * @param json The JSON Object codifying a single value array.
     * @return The unwrapped JSON Object.
     */
    public static JSONObject removeArrayWrapper(JSONObject json) {
        if (!json.has("value")) {
            return json;
        }

        JSONArray jsonArray = json.getJSONArray("value");

        if (jsonArray.isEmpty()) {
            return null;
        }

        return jsonArray.getJSONObject(0);
    }
}
