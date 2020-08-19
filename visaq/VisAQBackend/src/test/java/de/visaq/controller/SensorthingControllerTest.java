package de.visaq.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

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

    @Test
    public void multiBuildTest() {
        DatastreamController dc = new DatastreamController();
        assertEquals(2,
                dc.multiBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                        .getResourceAsStream("/multi_alive_datastreams.json")))).size());
        assertEquals(
                1, dc
                        .multiBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                                .getResourceAsStream("/multi_alive_datastreams_no_value.json"))))
                        .size());
    }
}
