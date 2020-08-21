package de.visaq.controller.math;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.geotools.process.vector.BarnesSurfaceInterpolator;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.visaq.model.PointDatum;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Handles the Interpolation of Observations using Barnes Interpolation.
 */
@RestController
public class DefaultInterpolation extends Interpolation {
    public static final String MAPPING = "/api/interpolation/default";

    @Override
    protected PointDatum[] interpolateCoordinates(Square square,
            ArrayList<Coordinate> coordinates) {
        Coordinate[] coordinatesArray = new Coordinate[coordinates.size()];
        coordinates.toArray(coordinatesArray);

        double scale = 3.0;
        int gridNum = (int) (square.maxExtent() / (0.03 / scale));

        GridTransform trans = new GridTransform(square, gridNum, gridNum);

        BarnesSurfaceInterpolator bsi = new BarnesSurfaceInterpolator(coordinatesArray);

        bsi.setLengthScale(0.02);
        bsi.setMinObservationCount(0);
        double invSqrt2 = 1 / Math.sqrt(2);
        double maxObservationDistance =
                invSqrt2 * Math.max(Math.abs(trans.transformX(1) - trans.transformX(0)),
                        Math.abs(trans.transformY(1) - trans.transformY(0)));
        bsi.setMaxObservationDistance(maxObservationDistance);
        bsi.setNoData(-99999);
        bsi.setPassCount(1);

        // Too much load on server
        if (gridNum > 15 * scale) {
            return null;
        }

        float[][] interpolated = bsi.computeSurface(square, gridNum, gridNum);

        PointDatum[] pointData = new PointDatum[gridNum * gridNum];
        int index = 0;

        for (int i = 0; i < gridNum; i++) {
            for (int j = 0; j < gridNum; j++) {
                /*
                 * Start on the right top of the square.
                 */
                pointData[index] =
                        new PointDatum(new Point2D.Double(trans.transformX(j), trans.transformY(i)),
                                interpolated[j][i]);
                /*
                 * pointData[index] = new PointDatum(new Point2D.Double(trans.transformX(j),
                 * trans.transformY(i)), (i % 2) == 1 ? (j % 2) == 1 ? 50 : 0 : (j % 2) == 0 ? 50 :
                 * 0);
                 */
                /*
                 * System.out.println(i + " " + j + " " + pointData[index].location + " " +
                 * pointData[index].datum);
                 */
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
     * @param defaultInterpolationWrapper Encapsulates the parameters for the interpolation
     * @return An Array of PointData Entities.
     */
    @CrossOrigin
    @PostMapping(MAPPING)
    public PointDatum[] interpolate(@RequestBody InterpolationWrapper defaultInterpolationWrapper) {
        return this.interpolate(defaultInterpolationWrapper.square,
                Instant.ofEpochMilli(defaultInterpolationWrapper.millis),
                defaultInterpolationWrapper.range, defaultInterpolationWrapper.observedProperty,
                defaultInterpolationWrapper.average, defaultInterpolationWrapper.variance);
    }
}
