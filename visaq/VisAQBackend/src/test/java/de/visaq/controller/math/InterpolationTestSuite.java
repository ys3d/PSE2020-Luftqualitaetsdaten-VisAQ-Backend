package de.visaq.controller.math;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.visaq.model.Square;

/**
 * Interpolation Suite.
 */
public class InterpolationTestSuite {
    public static Square INTERPOLATIONSQUARE;
    public static ArrayList<Coordinate> INTERPOLATIONCOORDINATES;

    static {
        try {
            INTERPOLATIONSQUARE = new ObjectMapper().readValue(
                    new File("src/test/resources/interpolation_square.json"), Square.class);
            INTERPOLATIONCOORDINATES = new ObjectMapper().readValue(
                    new File("src/test/resources/interpolation_coordinates.json"),
                    new TypeReference<ArrayList<Coordinate>>() {
                    });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
