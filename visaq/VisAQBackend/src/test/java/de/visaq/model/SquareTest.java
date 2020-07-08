package de.visaq.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Square}.
 */
public class SquareTest {
    @Test
    public void initTest() {
        Square s = new Square(4.2d, 3.4d, 6.7d, 8.4d);

        assertEquals(4.2d, s.getMaxX());
        assertEquals(3.4d, s.getMinX());
        assertEquals(6.7d, s.getMinY());
        assertEquals(8.4d, s.getMaxY());
    }

    @Test
    public void toStringTest() {
        Square s = new Square(4.2d, 3.4d, 6.7d, 8.4d);

        assertEquals("POLYGON((4,2 6,7, 3,4 6,7, 3,4 8,4, 4,2 8,4))", s.toString());
    }
}
