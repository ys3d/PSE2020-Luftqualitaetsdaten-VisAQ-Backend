package de.visaq.controller;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.Observation;
import de.visaq.model.sensorthings.ObservedProperty;
import de.visaq.model.sensorthings.Sensor;
import de.visaq.model.sensorthings.Thing;

/**
 * Encapsulates the control over Datastream objects.
 */
@RestController
public class DatastreamController extends SensorthingController<Datastream> {
    public static final String MAPPING = "/api/datastream";

    /**
     * Encapsulates a Thing and an ObservedProperty.
     */
    static class ThingAndObservedPropertyWrapper {
        public Thing thing;
        public ObservedProperty observedProperty;

        public ThingAndObservedPropertyWrapper() {
        }

        public ThingAndObservedPropertyWrapper(Thing thing, ObservedProperty observedProperty) {
            this.thing = thing;
            this.observedProperty = observedProperty;
        }
    }

    /**
     * Encapsulates a Sensor and an ObservedProperty.
     */
    static class SensorAndObservedPropertyWrapper {
        public Sensor sensor;
        public ObservedProperty observedProperty;

        public SensorAndObservedPropertyWrapper() {
        }

        public SensorAndObservedPropertyWrapper(Sensor sensor, ObservedProperty observedProperty) {
            this.sensor = sensor;
            this.observedProperty = observedProperty;
        }
    }

    @Override
    public ArrayList<Datastream> getAll() {
        return new MultiOnlineLink<Datastream>("/Datastreams", true).get(this);
    }

    /**
     * Retrieves all Datastream objects of the specified Thing.
     * 
     * @param thing Thing the Datastream objects are associated with.
     * @return An array of Datastream objects that were retrieved.
     */
    @PostMapping(value = MAPPING + "/all")
    public ArrayList<Datastream> getAll(@RequestBody Thing thing) {
        return thing.datastreamsLink.get(this);
    }

    /**
     * Retrieves all Datastream objects of the specified Sensor.
     * 
     * @param sensor Sensor the Datastream objects are associated with.
     * @return An array of Datastream objects that were retrieved.
     */
    @PostMapping(value = MAPPING + "/all")
    public ArrayList<Datastream> getAll(@RequestBody Sensor sensor) {
        return sensor.datastreamsLink.get(this);
    }

    /**
     * Retrieves the Datastream object for the ObservedProperty of the specified Thing.
     * 
     * @param wrapper Encapsulates the Thing and the ObservedProperty
     * @return The Datastream object that was retrieved.
     */
    @PostMapping(value = MAPPING)
    public Datastream get(@RequestBody ThingAndObservedPropertyWrapper wrapper) {
        return get(wrapper.thing, wrapper.observedProperty);
    }

    /**
     * Retrieves the Datastream object for the observed property of the specified Thing.
     * 
     * @param thing            Thing the Datastream is associated with.
     * @param observedProperty Observed Property the Datastream is associated with.
     * @return The Datastream object that was retrieved.
     */
    public Datastream get(Thing thing, ObservedProperty observedProperty) {
        return (Datastream) new SingleOnlineLink<Datastream>(MessageFormat.format(
                "/Things(''{0}'')/Datastreams?$filter=ObservedProperty/@iot.id eq ''{1}''",
                thing.id, observedProperty.id), true).get(this);
    }

    /**
     * Retrieves the Datastream object for the observed property of the specified Sensor.
     * 
     * @param wrapper Encapsulates the Sensor and the ObservedProperty
     * @return The Datastream object that was retrieved.
     */
    @PostMapping(value = MAPPING)
    public Datastream get(@RequestBody SensorAndObservedPropertyWrapper wrapper) {
        return get(wrapper.sensor, wrapper.observedProperty);
    }

    /**
     * Retrieves the Datastream object for the observed property of the specified Sensor.
     * 
     * @param sensor           Sensor the Datastream is associated with.
     * @param observedProperty Observed Property the Datastream is associated with.
     * @return The Datastream object that was retrieved.
     */
    public Datastream get(Sensor sensor, ObservedProperty observedProperty) {
        return (Datastream) new SingleOnlineLink<Datastream>(MessageFormat.format(
                "/Sensors(''{0}'')/Datastreams?$filter=ObservedProperty/@iot.id eq ''{1}''",
                sensor.id, observedProperty.id), true).get(this);
    }

    @PostMapping(value = MAPPING)
    @Override
    public Datastream get(@RequestBody IdWrapper idWrapper) {
        return get(idWrapper.id);
    }

    @Override
    public Datastream get(String id) {
        return (Datastream) new SingleOnlineLink<Datastream>(
                MessageFormat.format("/Datastreams(''{0}'')", id), true).get(this);
    }

    @Override
    public Datastream singleBuild(JSONObject json) {
        json = UtilityController.removeArrayWrapper(json);

        if (json == null) {
            return null;
        }

        SingleNavigationLink<Sensor> sensor = new SingleNavigationLink.Builder<Sensor>()
                .build("Sensor@iot.navigationLink", "Sensor", new SensorController(), json);
        SingleNavigationLink<Thing> thing = new SingleNavigationLink.Builder<Thing>()
                .build("Thing@iot.navigationLink", "Thing", new ThingController(), json);
        MultiNavigationLink<Observation> observations =
                new MultiNavigationLink.Builder<Observation>().build(
                        "Observations@iot.navigationLink", "Observations",
                        new ObservationController(), json);
        SingleNavigationLink<ObservedProperty> observedProperty =
                new SingleNavigationLink.Builder<ObservedProperty>().build(
                        "ObservedProperty@iot.navigationLink", "ObservedProperty",
                        new ObservedPropertyController(), json);

        Datastream datastream = new Datastream(json.getString("@iot.id"),
                json.getString("@iot.selfLink"), false, json.getString("name"),
                json.getString("description"), UtilityController.buildProperties(json),
                json.getString("observationType"), sensor, thing, observations,
                UtilityController.buildUnitOfMeasurement(json.getJSONObject("unitOfMeasurement")),
                observedProperty);
        return datastream;
    }
}
