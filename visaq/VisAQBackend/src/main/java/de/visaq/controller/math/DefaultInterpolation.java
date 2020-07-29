package de.visaq.controller.math;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.visaq.model.PointDatum;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Handles the Interpolation of Observations using Barnes Interpolation.
 */
@RestController
public class DefaultInterpolation extends Interpolation {
    public static final String MAPPING = "/api/interpolation/default";
    public static final int GRID_NUM = 10;

    static class DefaultInterpolationWrapper {
        public Square square;
        public long millis;
        public Duration range;
        public ObservedProperty observedProperty;

        public DefaultInterpolationWrapper() {
        }

        public DefaultInterpolationWrapper(@JsonProperty("x1") double x1, 
                @JsonProperty("x2") double x2, @JsonProperty("y1") double y1, 
                @JsonProperty("y2") double y2, @JsonProperty("millis") long millis,
                @JsonProperty("range") Duration range,
                @JsonProperty("observedProperty") ObservedProperty observedProperty) {
            this.square = new Square(x1, x2, y1, y2);
            this.millis = millis;
            this.range = range;
            this.observedProperty = observedProperty;
        }
    }

    @Override
    protected PointDatum[] interpolateCoordinates(Square square, 
            ArrayList<Coordinate> coordinates) {

        Coordinate[] c = coordinatesToArray(coordinates);
        NearestNeighborInterpolation nni = new NearestNeighborInterpolation(c, 0.05);

        // interpolated is in row major order.
        double[][] interpolated = 
                nni.computeSurface(square, GRID_NUM, GRID_NUM);

        PointDatum[] pointData = new PointDatum[(interpolated.length * interpolated[0].length)];
        int index = 0;


        for (int i = 0; i < interpolated.length; i++) {
            for (int j = 0; j < interpolated[0].length; j++) {
                System.out.println(interpolated[i][j]);
                GridTransform trans = new GridTransform(square, GRID_NUM, GRID_NUM);
                /*
                 * Start on the right top of the square.
                 */
                pointData[index] = new PointDatum(
                        new Point2D.Double(trans.transformX(j), trans.transformY(i)),
                        interpolated[i][j]);
                
                index++;
            }
        }
        return pointData;
    }

    @Override
    public PointDatum[] interpolate(Square square, Instant time, Duration range, 
            ObservedProperty observedProperty) {
        return super.interpolate(square, time, range, observedProperty);
    }

    /**
     * Returns interpolated data for a given viewport, time and Observed Property.
     * 
     * @param defaultInterpolationWrapper Encapsulates the parameters for the
     *                                    interpolation
     * @return An Array of PointData Entities.
     */
    @CrossOrigin
    @PostMapping(MAPPING)
    public PointDatum[] interpolate(
            @RequestBody DefaultInterpolationWrapper defaultInterpolationWrapper) {
        return this.interpolate(defaultInterpolationWrapper.square,
                Instant.ofEpochMilli(defaultInterpolationWrapper.millis), 
                defaultInterpolationWrapper.range,
                defaultInterpolationWrapper.observedProperty);
    }

    /*
     * Transforms an ArrayList of Coordinates into an Array.
     */
    private Coordinate[] coordinatesToArray(ArrayList<Coordinate> c) {
        int length = c.toArray().length;
        Coordinate[] coordinates = new Coordinate[length];
        for (int i = 0; i < length; i++) {
            coordinates[i] = c.get(i);
            System.out.println(coordinates[i].toString());
        }
        return coordinates;
    }
}
