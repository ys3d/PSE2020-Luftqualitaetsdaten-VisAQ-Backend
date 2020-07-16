package de.visaq.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;

import org.locationtech.jts.geom.Envelope;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    public Square(@JsonProperty("x1") double x1, @JsonProperty("x2") double x2,
            @JsonProperty("y1") double y1, @JsonProperty("y2") double y2) {
        super(x1, x2, y1, y2);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
        sym.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(sym);
        return MessageFormat.format("POLYGON (({0} {1}, {2} {3}, {4} {5}, {6} {7}, {0} {1}))",
                df.format(getMaxX()), df.format(getMinY()), df.format(getMinX()),
                df.format(getMinY()), df.format(getMinX()), df.format(getMaxY()),
                df.format(getMaxX()), df.format(getMaxY()));
    }
}
