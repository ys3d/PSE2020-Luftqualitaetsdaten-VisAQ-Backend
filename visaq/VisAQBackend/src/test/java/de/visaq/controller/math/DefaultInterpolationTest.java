package de.visaq.controller.math;

import static org.junit.Assert.assertNotEquals;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import de.visaq.model.PointDatum;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Tests {@link DefaultInterpolation}.
 */
public class DefaultInterpolationTest {
    private static final Interpolation CONTROLLER = new DefaultInterpolation();

    @Test
    public void ipTest() {
        Square square = new Square(10.8, 10.9, 48.29, 48.31);
        Duration range = Duration.ofHours(12);
        ObservedProperty op = new ObservedProperty("saqn:op:mcpm10",
                "https://api.smartaq.net/v1.0/ObservedProperties('saqn%3Aop%3Amcpm10')", false,
                "Mass concentration of Particulate Matter with a diameter of equal or less than "
                        + "10 micrometers in air.",
                "PM10 Mass Concentration", null,
                "http://cfconventions.org/Data/cf-standard-names/63/build/cf-standard-name-table.html#mass_concentration_of_pm10_ambient_aerosol_particles_in_air",
                null);
        PointDatum[] pointDatum = CONTROLLER.interpolate(square, Instant.now(), range, op);
        System.out.println("DefaultInterpolationTest" + pointDatum[0].datum);
        assertNotEquals(-999.0, pointDatum[0].datum);
    }
}
