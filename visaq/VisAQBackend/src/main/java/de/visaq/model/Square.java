package de.visaq.model;

import java.text.MessageFormat;

import org.locationtech.jts.geom.Envelope;

/**
 * Encapsulates a Square.
 */
public class Square extends Envelope {
    private static final long serialVersionUID = 6284175978327731458L;

    /**
     * See {@link Envelope#Envelope(double, double, double, double)}.
     * 
     * @param x1 {@link Envelope#Envelope(double, double, double, double)}
     * @param x2 {@link Envelope#Envelope(double, double, double, double)}
     * @param y1 {@link Envelope#Envelope(double, double, double, double)}
     * @param y2 {@link Envelope#Envelope(double, double, double, double)}
     */
    public Square(double x1, double x2, double y1, double y2) {
        super(x1, x2, y1, y2);
    }

    @Override
    public String toString() {
        return MessageFormat.format("POLYGON(({0} {1}, {2} {3}, {4} {5}, {6} {7}))", getMaxX(),
                getMinY(), getMinX(), getMinY(), getMinX(), getMaxY(), getMaxX(), getMaxY());
    }
}
