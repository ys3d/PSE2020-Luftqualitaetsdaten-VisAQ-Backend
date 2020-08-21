package de.visaq.controller.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;

import de.visaq.controller.ObservedPropertyController;
import de.visaq.controller.SensorthingsControllerTests;
import de.visaq.controller.math.Interpolation.InterpolationWrapper;
import de.visaq.model.PointDatum;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Tests {@link Interpolation}.
 */
public class InterpolationTest {

    @Test
    public void interpolationTest() {
        ObservedProperty op = new ObservedPropertyController()
                .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                        .getResourceAsStream("/alive_observedproperty.json"))));
        new Interpolation() {

            @Override
            protected PointDatum[] interpolateCoordinates(Square square,
                    ArrayList<Coordinate> coordinates) {
                assertEquals(0, coordinates.size());
                assertEquals(new Square(48, 49, 8, 9), square);
                return null;
            }
        }.interpolate(new Square(48, 49, 8, 9), Instant.EPOCH, Duration.ZERO, op, 0, 1000);

        new Interpolation() {

            @Override
            protected PointDatum[] interpolateCoordinates(Square square,
                    ArrayList<Coordinate> coordinates) {
                assertTrue(0 < coordinates.size());
                assertEquals(new Square(10, 11, 48, 49), square);
                return null;
            }
        }.interpolate(new Square(10, 11, 48, 49), Instant.now(), Duration.ofHours(1), op, 0, 1000);
    }

    @Test
    public void interpolationWrapperTest() {
        InterpolationWrapper w = new InterpolationWrapper();
        assertNull(w.square);
        assertEquals(0, w.millis);
        assertNull(w.range);
        assertNull(w.observedProperty);

        Duration d = Duration.ofMinutes(10);
        ObservedProperty op = new ObservedPropertyController()
                .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                        .getResourceAsStream("/alive_observedproperty.json"))));
        long millis = 500;
        w = new InterpolationWrapper(0.d, 2.d, 0.d, 4.d, millis, d, op, 0, 1000);

        assertEquals(new Square(0.d, 2.d, 0.d, 4.d), w.square);
        assertEquals(millis, millis);
        assertEquals(d, w.range);
        assertEquals(op, w.observedProperty);
    }

}
