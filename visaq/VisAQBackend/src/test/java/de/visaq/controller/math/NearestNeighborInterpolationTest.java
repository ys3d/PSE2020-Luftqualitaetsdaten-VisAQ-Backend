package de.visaq.controller.math;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import de.visaq.controller.ObservedPropertyController;
import de.visaq.controller.SensorthingsControllerTests;
import de.visaq.controller.math.Interpolation.InterpolationWrapper;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Tests {@link NearestNeighborInterpolation}.
 */
public class NearestNeighborInterpolationTest {

    @Test
    public void nearestNeighborInterpolationWrapperTest() {
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

    @Test
    public void interpolateTest() {
        ObservedProperty op = new ObservedPropertyController()
                .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                        .getResourceAsStream("/alive_observedproperty.json"))));
        InterpolationWrapper w = new InterpolationWrapper(47, 48, 9, 10, System.currentTimeMillis(),
                Duration.ofHours(1), op, 0, 1000);
        assertTrue(0 < new NearestNeighborInterpolation().interpolate(w).length);
    }

}
