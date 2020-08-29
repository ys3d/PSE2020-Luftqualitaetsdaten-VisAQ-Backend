package de.visaq.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.controller.ObservationController.TimeframeRun;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.Observation;
import de.visaq.model.sensorthings.ObservedProperty;
import de.visaq.model.sensorthings.Thing;

/**
 * Tests {@link TimeframeRun}.
 */
public class TimeFrameRunTest extends ResourceTest {
    private Thing[] things =
            new Thing[] { ALIVETHING, ALIVETHING, ALIVETHING, ALIVETHING, ALIVETHING };
    private Observation[] observations = new Observation[5];
    private ObservationController controller = new ObservationController();
    // 08.10.2018 10:00:00 ==> ofEpochMilli(1538985600000L)
    private Instant lower = Instant.ofEpochMilli(1538985600000L).minus(1, ChronoUnit.HOURS);
    private Instant upper = Instant.ofEpochMilli(1538985600000L).plus(1, ChronoUnit.HOURS);

    @BeforeEach
    public void before() {
        observations = new Observation[5];
    }

    @Test
    public void runNormalTest() {
        TimeframeRun t = controller.new TimeframeRun(1, 3, things, observations, lower, upper,
                ALIVEOBSERVEDPROPERTYPM10, 40, 10);
        t.run();
        assertEquals(5, observations.length);
        assertNull(observations[0]);
        assertNull(observations[4]);
        assertNotNull(observations[1]);

        for (int i = 2; i <= 3; i++) {
            assertEquals(observations[i], observations[i - 1]);
        }
        for (int i = 1; i <= 3; i++) {
            Datastream ds =
                    (Datastream) observations[i].datastreamLink.get(new DatastreamController());
            ObservedProperty op = (ObservedProperty) ds.observedPropertyLink
                    .get(new ObservedPropertyController());
            assertEquals(ALIVEOBSERVEDPROPERTYPM10.id, op.id);
        }
    }

    @Test
    public void upperOutOfBoundTest() {
        TimeframeRun t = controller.new TimeframeRun(4, 2, things, observations, lower, upper,
                ALIVEOBSERVEDPROPERTYPM10, 40, 10);
        t.run();
        assertEquals(5, observations.length);

        for (int i = 0; i < 4; i++) {
            assertNull(observations[i]);
        }
        assertNotNull(observations[4]);
        Datastream ds = (Datastream) observations[4].datastreamLink.get(new DatastreamController());
        ObservedProperty op =
                (ObservedProperty) ds.observedPropertyLink.get(new ObservedPropertyController());
        assertEquals(ALIVEOBSERVEDPROPERTYPM10.id, op.id);
    }

    @Test
    public void zeroSizeTest() {
        TimeframeRun t = controller.new TimeframeRun(0, 0, things, observations, lower, upper,
                ALIVEOBSERVEDPROPERTYPM10, 40, 10);
        t.run();
        assertEquals(5, observations.length);

        for (int i = 0; i < observations.length; i++) {
            assertNull(observations[i]);
        }
    }

    @Test
    public void illegalValueHigherTest() {
        TimeframeRun t = controller.new TimeframeRun(0, 1, things, observations, lower, upper,
                ALIVEOBSERVEDPROPERTYPM10, -5, 0);
        t.run();
        assertEquals(5, observations.length);

        for (int i = 0; i < observations.length; i++) {
            assertNull(observations[i]);
        }
    }

    @Test
    public void illegalValueLowerTest() {
        TimeframeRun t = controller.new TimeframeRun(0, 1, things, observations, lower, upper,
                ALIVEOBSERVEDPROPERTYPM10, 9000, 0);
        t.run();
        assertEquals(5, observations.length);

        for (int i = 0; i < observations.length; i++) {
            assertNull(observations[i]);
        }
    }

    @Test
    public void onPointValue() {
        Observation[] prefetchObservations = new Observation[5];
        TimeframeRun t = controller.new TimeframeRun(0, 1, things, prefetchObservations, lower,
                upper, ALIVEOBSERVEDPROPERTYPM10, 40, 10);
        t.run();
        assertEquals(5, prefetchObservations.length);
        assertNotNull(prefetchObservations[0]);
        for (int i = 1; i < prefetchObservations.length; i++) {
            assertNull(prefetchObservations[i]);
        }
        t = controller.new TimeframeRun(4, 1, things, observations, lower, upper,
                ALIVEOBSERVEDPROPERTYPM10, prefetchObservations[0].result, 0);
        t.run();
        assertEquals(5, observations.length);
        for (int i = 0; i < observations.length - 1; i++) {
            assertNull(observations[i]);
        }
        assertNotNull(observations[4]);
        assertEquals(prefetchObservations[0], observations[4]);
    }

}
