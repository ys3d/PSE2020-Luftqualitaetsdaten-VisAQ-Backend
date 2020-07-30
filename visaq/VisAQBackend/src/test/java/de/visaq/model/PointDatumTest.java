package de.visaq.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link PointDatum}.
 */
public class PointDatumTest {

    @Test
    public void initTest() {
        PointDatum pd = new PointDatum(new Point2D.Double(2, 5), 4.2d);

        assertEquals(new Point2D.Double(2, 5), pd.location);
        assertEquals(4.2d, pd.datum);
    }
}
