package de.visaq.model;

import java.awt.Point;

/**
 * Represents a specific datum at a point in space.
 */
public class PointDatum {
    public final Point location;
    public final double datum;

    /**
     * Constructs a new PointData object using the given location and datum.
     * 
     * @param location A point representing the location
     * @param datum    The datum
     */
    public PointDatum(Point location, double datum) {
        this.location = location;
        this.datum = datum;
    }

}
