package de.visaq.controller;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.Sensor;
import de.visaq.model.sensorthings.Thing;

/**
 * Encapsulates the control over Sensor objects.
 */
public class SensorController extends SensorthingController<Sensor> {
    public static final String MAPPING = "/api/sensor";

    @Override
    public ArrayList<Sensor> getAll() {
        return new MultiOnlineLink<Sensor>("/Sensors", true).get(this);
    }

    /**
     * Retrieves all Sensor objects associated with the specified Thing.
     * 
     * @param thing Thing the Sensor objects are associated with.
     * @return An array of Sensor objects that were retrieved.
     */
    @PostMapping(value = MAPPING + "/all", params = { "thing" })
    public ArrayList<Sensor> getAll(Thing thing) {
        return new MultiOnlineLink<Sensor>(
                MessageFormat.format("/Sensors?$filter=Datastreams/Thing/id eq ''{0}''", thing.id),
                true).get(this);
    }

    /**
     * Retrieves the Sensor object associated with the specified Datastream.
     * 
     * @param datastream Datastream the Sensor object is associated with.
     * @return The Sensor object that was retrieved.
     */
    @PostMapping(value = MAPPING, params = { "datastream" })
    public Sensor get(Datastream datastream) {
        return (Sensor) datastream.sensorLink.get(this);
    }

    @Override
    @PostMapping(value = MAPPING, params = { "id" })
    public Sensor get(String id) {
        return (Sensor) new SingleOnlineLink<Sensor>(MessageFormat.format("/Sensors(''{0}'')", id),
                true).get(this);
    }

    @Override
    public Sensor singleBuild(JSONObject json) {
        json = UtilityController.removeArrayWrapper(json);

        if (json == null) {
            return null;
        }

        MultiNavigationLink<Datastream> datastreams = new MultiNavigationLink.Builder<Datastream>()
                .build("Datastreams@iot.navigationLink", "Datastreams", new DatastreamController(),
                        json);

        Sensor sensor = new Sensor(json.getString("@iot.id"), json.getString("@iot.selfLink"),
                false, json.getString("description"), json.getString("name"),
                UtilityController.buildProperties(json), datastreams);
        return sensor;
    }
}
