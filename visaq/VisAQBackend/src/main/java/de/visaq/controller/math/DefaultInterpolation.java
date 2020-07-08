package de.visaq.controller.math;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;

import org.geotools.process.vector.BarnesSurfaceInterpolator;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.visaq.model.PointDatum;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Handles the Interpolation of Observations using Barnes Interpolation.
 */
public class DefaultInterpolation extends Interpolation {
    public static final String MAPPING = "/api/interpolation/default";

    @Override
    protected PointDatum[] interpolateCoordinates(Square square,
            ArrayList<Coordinate> coordinates) {
        // TODO
        float[][] interpolated = new BarnesSurfaceInterpolator((Coordinate[]) coordinates.toArray())
                .computeSurface(square, -1, -1);

        return null;
    }

    @PostMapping(MAPPING)
    @Override
    public PointDatum[] interpolate(@RequestParam Square square, @RequestParam Instant time,
            @RequestParam TemporalAmount range, @RequestParam ObservedProperty observedProperty) {
        return super.interpolate(square, time, range, observedProperty);
    }
}
