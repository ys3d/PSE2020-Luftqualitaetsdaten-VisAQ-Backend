package de.visaq.model.sensorthings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UnitOfMeasurement}.
 */
public class UnitOfMeasurementTest {
    @Test
    public void equalsTest() {
        UnitOfMeasurement um1 = new UnitOfMeasurement("name", "symbol", "definition");
        UnitOfMeasurement um2 = new UnitOfMeasurement("name", "symbol", "definition");

        assertTrue(um1.equals(um1));
        assertTrue(um1.equals(um2));

        assertFalse(um1.equals(null));

        um2 = new UnitOfMeasurement(null, "symbol", "definition");
        assertFalse(um1.equals(um2));

        um2 = new UnitOfMeasurement("name", null, "definition");
        assertFalse(um1.equals(um2));

        um2 = new UnitOfMeasurement("name", "symbol", null);
        assertFalse(um1.equals(um2));

        um2 = new UnitOfMeasurement("NOTname", "symbol", "definition");
        assertFalse(um1.equals(um2));

        um2 = new UnitOfMeasurement("name", "NOTsymbol", "definition");
        assertFalse(um1.equals(um2));

        um2 = new UnitOfMeasurement("name", "symbol", "NOTdefinition");
        assertFalse(um1.equals(um2));

        assertFalse(um1.equals(new Object()));
    }
}
