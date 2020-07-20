package de.visaq.model.sensorthings;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.spring.DedupingResolver;

/**
 * <p>
 * Representation of the Sensors entity in the OGC SensorThings API.
 * </p>
 * <p>
 * A Sensor in SensorThings API is an instrument that observes a property or phenomenon with the
 * goal of producing an estimate of the value of the property.
 * </p>
 * 
 * @see <a href=
 *      "https://developers.sensorup.com/docs/#sensors_post">https://developers.sensorup.com/docs/#sensors_post</a>
 */
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class,
        scope = Sensor.class, resolver = DedupingResolver.class)
public class Sensor extends Sensorthing<Sensor> implements SensorthingsProperties {
    public final String description;
    public final String name;
    public final MultiNavigationLink<Datastream> datastreamsLink;

    private final Map<String, Object> properties;

    /**
     * Constructs a new {@link Sensor}.
     * 
     * @param id              {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param selfUrl         {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param relative        {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param description     The description of the {@link Sensor}
     * @param name            The name of the {@link Sensor}
     * @param properties      Several properties of the {@link Sensor}
     * @param datastreamsLink Links to the {@link Datastream}
     */
    public Sensor(@JsonProperty("id") String id, @JsonProperty("selfUrl") String selfUrl,
            @JsonProperty("relative") boolean relative,
            @JsonProperty("description") String description, @JsonProperty("name") String name,
            @JsonProperty("properties") Map<String, Object> properties,
            @JsonProperty("datastreamsLink") MultiNavigationLink<Datastream> datastreamsLink) {
        super(id, selfUrl, relative);
        this.description = description;
        this.name = name;
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
