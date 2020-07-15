package de.visaq.controller;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.HistoricalLocation;
import de.visaq.model.sensorthings.Location;
import de.visaq.model.sensorthings.Thing;

/**
 * Encapsulates the control over Thing objects.
 */
@RestController
public class ThingController extends SensorthingController<Thing> {
    public static final String MAPPING = "/api/thing";

    @CrossOrigin
    @Override
    @PostMapping(value = MAPPING + "/all")
    public ArrayList<Thing> getAll() {
        MultiOnlineLink<Thing> multiLink = new MultiOnlineLink<Thing>("/Things", true);
        return multiLink.get(this);
    }

    /**
     * Retrieves the Thing objects spatially located inside the specified square.
     * 
     * @param square Covers the area of all allowed locations
     * @return An array of Thing objects that were retrieved.
     */
    @CrossOrigin
    @PostMapping(value = MAPPING + "/all/square")
    public ArrayList<Thing> getAll(@RequestBody Square square) {
        return new MultiOnlineLink<Thing>(MessageFormat
                .format("/Thing?$filter=st_within(location, geography''{{0}}'')", square), true)
                        .get(this);
    }

    @CrossOrigin
    @Override
    @PostMapping(value = MAPPING + "/id")
    public Thing get(@RequestBody IdWrapper idWrapper) {
        return get(idWrapper.id);
    }

    @Override
    public Thing get(String id) {
        return (Thing) new SingleOnlineLink<Thing>(MessageFormat.format("/Things(''{0}'')", id),
                true).get(this);
    }

    @Override
    public Thing singleBuild(JSONObject json) {
        json = UtilityController.removeArrayWrapper(json);

        if (json == null) {
            return null;
        }

        MultiNavigationLink<Datastream> datastreams = new MultiNavigationLink.Builder<Datastream>()
                .build("Datastreams@iot.navigationLink", "Datastreams", new DatastreamController(),
                        json);
        MultiNavigationLink<HistoricalLocation> historicalLocations =
                new MultiNavigationLink.Builder<HistoricalLocation>().build(
                        "HistoricalLocations@iot.navigationLink", "HistoricalLocations",
                        new HistoricalLocationController(), json);
        MultiNavigationLink<Location> locations = new MultiNavigationLink.Builder<Location>()
                .build("Locations@iot.navigationLink", "Locations", new LocationController(), json);

        Thing thing = new Thing(json.getString("@iot.id"), json.getString("@iot.selfLink"), false,
                json.getString("description"), json.getString("name"),
                UtilityController.buildProperties(json), datastreams, historicalLocations,
                locations);
        return thing;
    }
}
