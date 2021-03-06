package de.visaq.model.sensorthings;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.spring.DedupingResolver;

/**
 * <p>
 * Representation of the Datastream entity in the OGC SensorThings API.
 * </p>
 * <p>
 * Datastreams group {@link Observation}s on the same {@link ObservedProperty} and {@link Sensor}
 * </p>
 * 
 * @see <a href=
 *      "https://developers.sensorup.com/docs/#datastreams_post">https://developers.sensorup.com/docs/#datastreams_post</a>
 */
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class,
        scope = Datastream.class, resolver = DedupingResolver.class)
public class Datastream extends Sensorthing<Datastream> implements SensorthingsProperties {
    public final String name;
    public final String description;
    public final UnitOfMeasurement unitOfMeasurement;
    // URL of some definition regarding this observation
    public final String observationTypeLink;
    public final MultiNavigationLink<Observation> observationsLink;
    public final SingleNavigationLink<Sensor> sensorLink;
    public final SingleNavigationLink<Thing> thingLink;
    public final SingleNavigationLink<ObservedProperty> observedPropertyLink;
    private final Map<String, Object> properties;

    /**
     * Constructs a new {@link Datastream}.
     * 
     * @param id                   {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param selfUrl              {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param relative             {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param name                 The name of the {@link Datastream}
     * @param description          The description of the {@link Datastream}
     * @param properties           Several properties given by the database
     * @param observationTypeLink  URL leading to a documentation about the Observation Type
     * @param sensorLink           Link to the {@link Sensor}
     * @param thingLink            Link to the {@link Thing}
     * @param observationsLink     Links to the {@link Observation}
     * @param unitOfMeasurement    The Unit of Measurement
     * @param observedPropertyLink Link to the {@link ObservedProperty}
     */
    public Datastream(@JsonProperty("id") String id, @JsonProperty("selfUrl") String selfUrl,
            @JsonProperty("relative") boolean relative, @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("properties") Map<String, Object> properties,
            @JsonProperty("observationTypeLink") String observationTypeLink,
            @JsonProperty("sensorLink") SingleNavigationLink<Sensor> sensorLink,
            @JsonProperty("thingLink") SingleNavigationLink<Thing> thingLink,
            @JsonProperty("observationsLink") MultiNavigationLink<Observation> observationsLink,
            @JsonProperty("unitOfMeasurement") UnitOfMeasurement unitOfMeasurement,
            @JsonProperty("observedPropertyLink") SingleNavigationLink<ObservedProperty> observedPropertyLink) {
        super(id, selfUrl, relative);
        this.name = name;
        this.description = description;
        this.unitOfMeasurement = unitOfMeasurement;
        this.observationTypeLink = observationTypeLink;
        this.observationsLink = observationsLink;
        this.sensorLink = sensorLink;
        this.thingLink = thingLink;
        this.observedPropertyLink = observedPropertyLink;
        this.properties = properties;
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
