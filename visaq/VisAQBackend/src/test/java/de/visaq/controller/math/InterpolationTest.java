package de.visaq.controller.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        }.interpolate(new Square(48, 49, 8, 9), Instant.EPOCH, Duration.ZERO, op);

        new Interpolation() {

            @Override
            protected PointDatum[] interpolateCoordinates(Square square,
                    ArrayList<Coordinate> coordinates) {
                assertTrue(0 < coordinates.size());
                assertEquals(new Square(10, 11, 48, 49), square);
                return null;
            }
        }.interpolate(new Square(10, 11, 48, 49), Instant.now(), Duration.ofHours(1), op);
    }

}
