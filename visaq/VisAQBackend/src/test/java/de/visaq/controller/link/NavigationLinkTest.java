package de.visaq.controller.link;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.RestConstants;
import de.visaq.controller.DatastreamController;

/**
 * Tests {@link NavigationLink}.
 */
public class NavigationLinkTest extends ResourceTest {

    @Test
    public void initTest() {
        TestNavigationLink link = new TestNavigationLink("url", false);
        assertEquals("url", link.url);
        link = new TestNavigationLink("url", true);
        assertEquals(RestConstants.ENTRY_POINT + "url", link.url);
    }

    @Test
    public void getJsonTest() {
        TestNavigationLink link = new TestNavigationLink(ALIVEDATASTREAM.selfUrl, false);
        JSONObject json = link.getJson();
        assertNotNull(json);
        assertEquals(ALIVEDATASTREAM, new DatastreamController().singleBuild(json));
        link = new TestNavigationLink("/illegal", true);
        assertNull(link.getJson());
    }

    private class TestNavigationLink extends NavigationLink<TestSensorThing> {

        public TestNavigationLink(String url, boolean relative) {
            super(url, relative);
            // TODO Auto-generated constructor stub
        }

    }
}
