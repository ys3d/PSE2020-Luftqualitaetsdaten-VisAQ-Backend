package de.visaq.controller.math;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    static class InterpolationWrapper {
        public Square square;
        public long millis;
        public Duration range;
        public ObservedProperty observedProperty;
        public double average;
        public double variance;

        public InterpolationWrapper() {
        }

        public InterpolationWrapper(@JsonProperty("x1") double x1, @JsonProperty("x2") double x2,
                @JsonProperty("y1") double y1, @JsonProperty("y2") double y2,
                @JsonProperty("millis") long millis, @JsonProperty("range") Duration range,
                @JsonProperty("observedProperty") ObservedProperty observedProperty,
                @JsonProperty("average") double average,
                @JsonProperty("variance") double variance) {
            this.square = new Square(x1, x2, y1, y2);
            this.millis = millis;
            this.range = range;
            this.observedProperty = observedProperty;
            this.average = average;
            this.variance = variance;
        }
    }

    /**
     * Interpolates all Observations within the specified square and time range.
     * 
     * @param square           Covers the area of all allowed locations
     * @param time             A point in time
     * @param range            The Observation must have been recorded in [time - range, time +
     *                         range]
     * @param observedProperty The ObservedProperty that was observed
     * @param average          Assumed average of the ObservedProperty
     * @param variance         Assumed variance of the ObservedProperty
     * @return An array of PointData
     */
    public PointDatum[] interpolate(Square square, Instant time, Duration range,
            ObservedProperty observedProperty, double average, double variance) {

        ThingController thingController = new ThingController();
        ArrayList<Thing> things = thingController.getAll(square);
        // Also filters probably borken sensors with variance and average value
        ArrayList<Observation> observations = new ObservationController().getAll(things, time,
                range, observedProperty, average, variance);
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        LocationController locationController = new LocationController();

        for (int i = 0; i < things.toArray().length; i++) {
            if (!things.get(i).locationsLink.get(locationController).isEmpty()) {

                Point2D.Double p =
                        things.get(i).locationsLink.get(locationController).get(0).location;

                if (p != null && observations.get(i) != null) {
                    Coordinate c = new Coordinate(p.getX(), p.getY(), observations.get(i).result);
                    coordinates.add(c);
                }
            }
        }

        if (coordinates.isEmpty()) {
            return null;
        }

        return interpolateCoordinates(square, coordinates);
    }

    /**
     * Interpolates a Coordinate ArrayList inside the specified square.
     * 
     * @param square      Covers the x,y-plane of all allowed coordinates
     * @param coordinates An ArrayList of Coordinate objects, each Coordinate represents a Point in
     *                    space (x,y) and value (z)
     * @return An array of PointData
     */
    protected abstract PointDatum[] interpolateCoordinates(Square square,
            ArrayList<Coordinate> coordinates);
}
