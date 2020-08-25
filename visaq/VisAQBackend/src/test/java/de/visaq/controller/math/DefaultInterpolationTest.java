package de.visaq.controller.math;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.controller.math.Interpolation.InterpolationWrapper;
import de.visaq.model.PointDatum;

/**
 * Tests {@link DefaultInterpolation}.
 */
public class DefaultInterpolationTest extends ResourceTest {
    private static final DefaultInterpolation CONTROLLER = new DefaultInterpolation();

    @Test
    public void testDefaultInterpolationInterpolateCoordinates() {
        PointDatum[] result =
                CONTROLLER.interpolateCoordinates(INTERPOLATIONSQUARE, INTERPOLATIONCOORDINATES);
        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    public void interpolateTest() {
        Instant instant = Instant.now().minus(2, ChronoUnit.HOURS);
        PointDatum[] resultWrapper =
                CONTROLLER.interpolate(new InterpolationWrapper(INTERPOLATIONSQUARE.getMinX(),
                        INTERPOLATIONSQUARE.getMaxX(), INTERPOLATIONSQUARE.getMinY(),
                        INTERPOLATIONSQUARE.getMaxY(), instant.toEpochMilli(), Duration.ofHours(1),
                        ALIVEOBSERVEDPROPERTY, 0, 1000));
        PointDatum[] resultDirect = CONTROLLER.interpolate(INTERPOLATIONSQUARE, instant,
                Duration.ofHours(1), ALIVEOBSERVEDPROPERTY, 0, 1000);
        assertNotNull(resultWrapper);
        assertNotNull(resultDirect);
        assertEquals(resultWrapper.length, resultDirect.length);
        for (int i = 0; i < resultDirect.length; i++) {
            assertEquals(resultWrapper[i].datum, resultDirect[i].datum);
            assertEquals(resultWrapper[i].location, resultDirect[i].location);
        }
    }

}
