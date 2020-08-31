package de.visaq.controller.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Envelope;

/**
 * Tests {@link GridTransform}.
 */
public class GridTransformTest {

    @Test
    public void initTest() {
        Envelope e = new Envelope(0, 2, 0, 2);
        assertNotNull(new GridTransform(e, 2, 2));
    }

    @Test
    public void transformXTest() {
        Envelope e = new Envelope(0, 4, 0, 4);
        GridTransform g = new GridTransform(e, 5, 5);
        assertEquals(0, g.transformX(0));
        assertEquals(2, g.transformX(2));
        assertEquals(1, g.transformX(1));
        assertEquals(4, g.transformX(4));
        assertEquals(4, g.transformX(5));
        assertEquals(4, g.transformX(6));

        g = new GridTransform(e, 9, 9);
        assertEquals(0.5, g.transformX(1));
    }

    @Test
    public void transformYTest() {
        Envelope e = new Envelope(0, 4, 0, 4);
        GridTransform g = new GridTransform(e, 5, 5);
        assertEquals(0, g.transformY(0));
        assertEquals(2, g.transformY(2));
        assertEquals(1, g.transformY(1));
        assertEquals(4, g.transformY(4));
        assertEquals(4, g.transformY(5));
        assertEquals(4, g.transformY(6));

        g = new GridTransform(e, 9, 9);
        assertEquals(0.5, g.transformY(1));
    }

}
