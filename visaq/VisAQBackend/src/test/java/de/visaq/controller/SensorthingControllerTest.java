package de.visaq.controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiLocalLink;
import de.visaq.controller.link.SingleLocalLink;
import de.visaq.model.sensorthings.Datastream;

/**
 * Tests {@link SensorController}.
 */
public class SensorthingControllerTest {

    @Test
    public void idWrapperTest() {
        IdWrapper wrapper = new IdWrapper();
        assertNull(wrapper.id);

        wrapper = new IdWrapper("id");
        assertEquals("id", wrapper.id);
    }

    @Test
    public void getMultiNaviagtionLinkTest() {
        ArrayList<Datastream> ds = new ArrayList<Datastream>();
        MultiLocalLink<Datastream> link = new MultiLocalLink<Datastream>("url", true, ds);
        assertEquals(ds, new DatastreamController().get(link));
    }

    @Test
    public void getSingleNavigationLinkTest() {
        Datastream ds = SensorthingsControllerTests.ALIVEDATASTREAM;
        SingleLocalLink<Datastream> link = new SingleLocalLink<Datastream>("url", true, ds);
        assertEquals(ds, new DatastreamController().get(link));
    }
}
