package de.visaq.controller.math;

import org.locationtech.jts.geom.Envelope;

/**
 * A transformation between two parallel coordinate systems, one defined by an
 * {@link Envelope} and one defined by a discrete zero-based grid representing
 * the same area as the envelope.
 */
class GridTransform {

    private Envelope env;

    private int xcoord;

    private int ycoord;

    private double dx;

    private double dy;

    /**
     * Sole constructor of the Class GridTransform.
     *
     * @param env   The envelope defining one coordinate system
     * @param x     The number of cells along the X axis of the grid
     * @param y     The number of cells along the Y axis of the grid
     */
    public GridTransform(Envelope env, int x, int y) {
        this.env = env;
        this.xcoord = x;
        this.ycoord = y;
        dx = env.getWidth() / (x - 1);
        dy = env.getHeight() / (y - 1);
    }

    /**
     * Computes the X ordinate of the i'th grid column.
     *
     * @param i     The index of a grid column
     * @return The X ordinate of the column
     */
    public double transformX(int i) {
        if (i >= xcoord - 1) {
            return env.getMaxX();
        }
        return env.getMinX() + i * dx;
    }

    /**
     * Computes the Y ordinate of the i'th grid row.
     *
     * @param j     The index of a grid row
     * @return The Y ordinate of the row
     */
    public double transformY(int j) {
        if (j >= ycoord - 1) {
            return env.getMaxY();
        }
        return env.getMinY() + j * dy;
    }
}