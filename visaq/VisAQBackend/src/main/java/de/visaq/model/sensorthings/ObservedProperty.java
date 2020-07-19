package de.visaq.model.sensorthings;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.visaq.controller.link.MultiNavigationLink;

/**
 * <p>
 * Representation of the ObservedProperty entity in the OGC SensorThings API.
 * </p>
 * <p>
 * An ObservedProperty specifies the phenomenon of an {@link Observation}.
 * </p>
 * 
 * @see <a href=
 *      "https://developers.sensorup.com/docs/#observedProperties_post">https://developers.sensorup.com/docs/#observedProperties_post</a>
 */
public class ObservedProperty extends Sensorthing<ObservedProperty>
        implements SensorthingsProperties {
    public final String name;
    public final String definition;
    public final String description;
    private final Map<String, Object> properties;
    public final MultiNavigationLink<Datastream> datastreamsLink;

    /**
     * Constructs a new {@link ObservedProperty}.
     * 
     * @param id              {@link Sensorthing#Sensorthings(String, String, boolean)}
     * @param selfUrl         {@link Sensorthing#Sensorthings(String, String, boolean)}
     * @param relative        {@link Sensorthing#Sensorthings(String, String, boolean)}
     * @param description     The description of the {@link ObservedProperty}
     * @param name            The name of the {@link ObservedProperty}
     * @param properties      The properties of the Observed Property
     * @param definition      A URL which links to a definition of the {@link ObservedProperty}
     * @param datastreamsLink Links to {@link Datastream}s
     */
    public ObservedProperty(@JsonProperty("id") String id, @JsonProperty("selfUrl") String selfUrl,
            @JsonProperty("relative") boolean relative,
            @JsonProperty("description") String description, @JsonProperty("name") String name,
            @JsonProperty("properties") Map<String, Object> properties,
            @JsonProperty("definition") String definition,
            @JsonProperty("datastreamsLink") MultiNavigationLink<Datastream> datastreamsLink) {
        super(id, selfUrl, relative);
        this.name = name;
        this.definition = definition;
        this.description = description;
        this.properties = properties;
        this.datastreamsLink = datastreamsLink;
    }

    @Override
    public Object getPropertyByKey(String key) {
        return properties.get(key);
    }

    @Override
    public boolean hasProperties(String key) {
        return properties.containsKey(key);
    }

}