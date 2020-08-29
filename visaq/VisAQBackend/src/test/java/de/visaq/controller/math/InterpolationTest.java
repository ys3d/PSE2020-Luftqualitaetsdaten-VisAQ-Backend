package de.visaq.controller.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;

import de.visaq.ResourceTest;
import de.visaq.controller.math.Interpolation.InterpolationWrapper;
import de.visaq.model.PointDatum;
import de.visaq.model.Square;

/**
 * Tests {@link Interpolation}.
 */
public class InterpolationTest extends ResourceTest {

    @Test
    public void interpolationValuePassingTest() {
        new Interpolation() {
            @Override
            protected PointDatum[] interpolateCoordinates(Square square,
                    ArrayList<Coordinate> coordinates) {
                assertEquals(0, coordinates.size());
                assertEquals(new Square(48, 49, 8, 9), square);
                return null;
            }
        }.interpolate(new Square(48, 49, 8, 9), Instant.EPOCH, Duration.ZERO, ALIVEOBSERVEDPROPERTY,
                0, 1000);

        new Interpolation() {
            @Override
            protected PointDatum[] interpolateCoordinates(Square square,
                    ArrayList<Coordinate> coordinates) {
                assertTrue(0 < coordinates.size());
                assertEquals(new Square(10, 11, 48, 49), square);
                return null;
            }
        }.interpolate(new Square(10, 11, 48, 49), Instant.now(), Duration.ofHours(1),
                ALIVEOBSERVEDPROPERTY, 0, 1000);
    }

    @Test
    public void interpolateWithDataTest() {
        int average = 75;
        int variance = 25;
        new Interpolation() {
            @Override
            protected PointDatum[] interpolateCoordinates(Square square,
                    ArrayList<Coordinate> coordinates) {
                assertTrue(0 < coordinates.size());
                for (int i = 0; i < coordinates.size(); i++) {
                    assertTrue(coordinates.get(i).x <= square.getMaxX()
                            && coordinates.get(i).x >= square.getMinX());
                    assertTrue(coordinates.get(i).y <= square.getMaxY()
                            && coordinates.get(i).y >= square.getMinY());
                    assertTrue(coordinates.get(i).z <= average + variance * 10
                            && coordinates.get(i).z >= average - variance * 10);
                }
                assertEquals(INTERPOLATIONSQUARE, square);
                return null;
            }
        }.interpolate(INTERPOLATIONSQUARE, Instant.now(), Duration.ofHours(2),
                ALIVEOBSERVEDPROPERTY, average, variance);
    }

    @Test
    public void interpolationWrapperTest() {
        InterpolationWrapper w = new InterpolationWrapper();
        assertNull(w.square);
        assertEquals(0, w.millis);
        assertNull(w.range);
        assertNull(w.observedProperty);

        Duration d = Duration.ofMinutes(10);
        long millis = 500;
        w = new InterpolationWrapper(0.d, 2.d, 0.d, 4.d, millis, d, ALIVEOBSERVEDPROPERTY, 0, 1000);

        assertEquals(new Square(0.d, 2.d, 0.d, 4.d), w.square);
        assertEquals(millis, millis);
        assertEquals(d, w.range);
        assertEquals(ALIVEOBSERVEDPROPERTY, w.observedProperty);
    }

    @Test
    public void interpolateIllegalValueToLowTest() {
        new Interpolation() {
            @Override
            protected PointDatum[] interpolateCoordinates(Square square,
                    ArrayList<Coordinate> coordinates) {
                assertEquals(0, coordinates.size());
                assertEquals(INTERPOLATIONSQUARE, square);
                return null;
            }
        }.interpolate(INTERPOLATIONSQUARE, Instant.now(), Duration.ofHours(2),
                ALIVEOBSERVEDPROPERTY, 5000, 0);
    }

    @Test
    public void interpolateIllegalValueToHighTest() {
        new Interpolation() {
            @Override
            protected PointDatum[] interpolateCoordinates(Square square,
                    ArrayList<Coordinate> coordinates) {
                assertEquals(0, coordinates.size());
                assertEquals(INTERPOLATIONSQUARE, square);
                return null;
            }
        }.interpolate(INTERPOLATIONSQUARE, Instant.now(), Duration.ofHours(2),
                ALIVEOBSERVEDPROPERTY, -5000, 0);
    }
}
