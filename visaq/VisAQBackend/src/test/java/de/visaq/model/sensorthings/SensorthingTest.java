package de.visaq.model.sensorthings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Sensorthing}.
 */
public class SensorthingTest {

    @Test
    public void equalsTest() {
        TestThing t1 = new TestThing("id1");
        TestThing t2 = new TestThing("id1");

        assertTrue(t1.equals(t1));
        assertTrue(t1.equals(t2));

        TestThing t3 = new TestThing("id2");
        TestThing tnull = new TestThing(null);

        assertFalse(t1.equals(t3));
        assertFalse(t1.equals(tnull));
        assertFalse(t1.equals(null));
        assertFalse(t1.equals(new Object()));
    }

    /**
     * {@link TestThing} to test {@link Sensorthing}. The {@link TestThing} does not have any
     * functionality beside the one that's implemented in {@link Sensorthing}
     */
    private class TestThing extends Sensorthing<TestThing> {

        /**
         * Initializes a new {@link TestThing}.
         * 
         * @param id       The id
         * @param selfUrl  The URL selfLink
         * @param relative If the given URL is relative
         */
        public TestThing(String id, String selfUrl, boolean relative) {
            super(id, selfUrl, relative);
        }

        /**
         * Initializes a new {@link TestThing} with all null values beside the given ones.
         * 
         * @param id The id of the {@link Sensorthing}
         */
        public TestThing(String id) {
            super(id, null, false);
        }

    }
}
