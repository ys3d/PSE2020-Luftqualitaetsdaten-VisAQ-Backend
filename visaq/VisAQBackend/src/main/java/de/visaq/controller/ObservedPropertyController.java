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
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Encapsulates the control over ObservedProperty objects.
 */
@RestController
public class ObservedPropertyController extends SensorthingController<ObservedProperty> {
    public static final String MAPPING = "/api/observedProperty";

    @Override
    public ArrayList<ObservedProperty> getAll() {
        return new MultiOnlineLink<ObservedProperty>("/ObservedProperties", true).get(this);
    }

    /**
     * Retrieves the ObservedProperty entity associated with the given Datastream entity.
     * 
     * @param datastream The Datastream entity
     * @return The ObservedProperty
     */
    @CrossOrigin
    @PostMapping(value = MAPPING + "/datastream")
    public ObservedProperty get(@RequestBody Datastream datastream) {
        return (ObservedProperty) datastream.observedPropertyLink.get(this);
    }

    @CrossOrigin
    @Override
    @PostMapping(value = MAPPING + "/id")
    public ObservedProperty get(@RequestBody IdWrapper idWrapper) {
        return get(idWrapper.id);
    }

    @Override
    public ObservedProperty get(String id) {
        return (ObservedProperty) new SingleOnlineLink<ObservedProperty>(
                MessageFormat.format("/ObservedProperties(''{0}'')", id), true).get(this);
    }

    @Override
    public ObservedProperty singleBuild(JSONObject json) {
        json = UtilityController.removeArrayWrapper(json);

        if (json == null) {
            return null;
        }

        MultiNavigationLink<Datastream> datastreams = new MultiNavigationLink.Builder<Datastream>()
                .build("Datastreams@iot.navigationLink", "Datastreams", new DatastreamController(),
                        json);

        ObservedProperty observedProperty = new ObservedProperty(json.getString("@iot.id"),
                json.getString("@iot.selfLink"), false, json.getString("description"),
                json.getString("name"), UtilityController.buildProperties(json),
                json.getString("definition"), datastreams);
        return observedProperty;
    }
}
