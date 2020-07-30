package de.visaq.controller.math;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;

import de.visaq.controller.LocationController;
import de.visaq.controller.ObservationController;
import de.visaq.controller.ThingController;
import de.visaq.model.PointDatum;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.Observation;
import de.visaq.model.sensorthings.ObservedProperty;
import de.visaq.model.sensorthings.Thing;

/**
 * Handles the Interpolation of Observations.
 */
public abstract class Interpolation {
    /**
     * Interpolates all Observations within the specified square and time range.
     * 
     * @param square           Covers the area of all allowed locations
     * @param time             A point in time
     * @param range            The Observation must have been recorded in [time -
     *                         range, time + range]
     * @param observedProperty The ObservedProperty that was observed
     * @return An array of PointData
     */
    public PointDatum[] interpolate(Square square, Instant time, 
            Duration range, ObservedProperty observedProperty) {

        ThingController thingController = new ThingController();
        ArrayList<Thing> things = thingController.getAll(square);

        ArrayList<Observation> observations = new ObservationController()
                .getAll(things, time, range, observedProperty);
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        LocationController locationController = new LocationController();

        for (int i = 0; i < things.toArray().length; i++) {
            if (!things.get(i).locationsLink.get(locationController).isEmpty()) {

                Point2D.Double p = things.get(i).locationsLink.get(locationController)
                        .get(0).location;

                if (p != null && observations.get(i) != null) {
                    Coordinate c = new Coordinate(p.getX(), p.getY(), observations.get(i).result);
                    coordinates.add(c);
                }
            }
        }

        return interpolateCoordinates(square, coordinates);
    }

    /**
     * Interpolates a Coordinate ArrayList inside the specified square.
     * 
     * @param square      Covers the x,y-plane of all allowed coordinates
     * @param coordinates An ArrayList of Coordinate objects, each Coordinate
     *                    represents a Point in space (x,y) and value (z)
     * @return An array of PointData
     */
    protected abstract PointDatum[] interpolateCoordinates(Square square, 
            ArrayList<Coordinate> coordinates);
}
