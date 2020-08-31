package de.visaq.controller.math;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.visaq.model.PointDatum;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * The class applies nearest neighbor interpolation to a discrete grid.
 */
@RestController
public class NearestNeighborInterpolation extends Interpolation {
    public static final String MAPPING = "/api/interpolation/nearestNeighbor";
    public static final int GRID_NUM = 10;

    /*
     * The default value if the coordinates are empty or the minimal distance of the grid point to a
     * sensor is bigger than maxDistance.
     */
    public final double defaultValue = -99999;

    public final double maxDistance = 0.05;

    private Coordinate[] coordinates = new Coordinate[0];

    @Override
    protected PointDatum[] interpolateCoordinates(Square square,
            ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates.toArray(this.coordinates);

        // interpolated is in row major order.
        double[][] interpolated = computeSurface(square, GRID_NUM, GRID_NUM);

        PointDatum[] pointData = new PointDatum[(interpolated.length * interpolated[0].length)];
        int index = 0;

        for (int i = 0; i < interpolated.length; i++) {
            for (int j = 0; j < interpolated[0].length; j++) {
                GridTransform trans = new GridTransform(square, GRID_NUM, GRID_NUM);
                /*
                 * Start on the right top of the square.
                 */
                pointData[index] =
                        new PointDatum(new Point2D.Double(trans.transformX(j), trans.transformY(i)),
                                interpolated[i][j]);

                index++;
            }
        }
        return pointData;
    }

    @Override
    public PointDatum[] interpolate(Square square, Instant time, Duration range,
            ObservedProperty observedProperty, double average, double variance) {
        return super.interpolate(square, time, range, observedProperty, average, variance);
    }

    /**
     * Returns interpolated data for a given viewport, time and Observed Property.
     * 
     * @param interpolationWrapper Encapsulates the parameters for the interpolation
     * @return An Array of PointData Entities.
     */
    @CrossOrigin
    @PostMapping(MAPPING)
    public PointDatum[] interpolate(@RequestBody InterpolationWrapper interpolationWrapper) {
        return this.interpolate(interpolationWrapper.square,
                Instant.ofEpochMilli(interpolationWrapper.millis), interpolationWrapper.range,
                interpolationWrapper.observedProperty, interpolationWrapper.average,
                interpolationWrapper.variance);
    }

    /**
     * Projects the measurements on a grid using nearest neighbor interpolation.
     * 
     * @param srcEnv The area of the grid
     * @param x      The number of grid points along the x axis
     * @param y      The number of grid points along the y axis
     * @return A grid of interpolated values
     */
    private double[][] computeSurface(Envelope srcEnv, int x, int y) {

        GridTransform trans = new GridTransform(srcEnv, x, y);

        double[][] grid = new double[x][y];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                double xcoord = trans.transformX(j);
                double ycoord = trans.transformY(i);

                grid[i][j] = getClosestCoordinateValue(xcoord, ycoord);
            }
        }
        return grid;
    }

    /*
     * Method finds the variable closest to the gridPoint.
     */
    private double getClosestCoordinateValue(double x, double y) {

        Coordinate p = new Coordinate(x, y);

        if (coordinates.length == 0) {
            return defaultValue;
        }

        double minDistance = p.distance(coordinates[0]);
        int minDistanceIndex = 0;

        for (int i = 0; i < coordinates.length; i++) {

            double dist = p.distance(coordinates[i]);

            if (dist < minDistance) {
                minDistance = dist;
                minDistanceIndex = i;
            }
        }
        /*
         * Checks if the minimal Distance is big enough
         */
        if (minDistance < maxDistance) {
            return coordinates[minDistanceIndex].getZ();
        } else {
            return defaultValue;
        }
    }
}
