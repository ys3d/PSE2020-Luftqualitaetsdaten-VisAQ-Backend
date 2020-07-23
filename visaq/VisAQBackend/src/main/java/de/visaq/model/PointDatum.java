package de.visaq.model;

import java.awt.geom.Point2D;

/**
 * Represents a specific datum at a point in space.
 */
public class PointDatum {
	public final Point2D location;
    public final double datum;

    /**
     * Constructs a new PointData object using the given location and datum.
     * 
     * @param location A point representing the location
     * @param datum    The datum
     */
    public PointDatum(Point2D location, double datum) {
        this.location = location;
        this.datum = datum;
    }

}
