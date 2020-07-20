package de.visaq.model.sensorthings;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import de.visaq.controller.link.SingleLocalLink;

/**
 * Encapsulates all Sensorthings objects.
 * 
 * @param <SensorthingT> A class that implements the abstract class Sensorthings, used for f-bounded
 *                       quantification.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({ @JsonSubTypes.Type(value = Datastream.class, name = "Datastream"),
        @JsonSubTypes.Type(value = FeatureOfInterest.class, name = "FeatureOfInterest"),
        @JsonSubTypes.Type(value = HistoricalLocation.class, name = "HistoricalLocation"),
        @JsonSubTypes.Type(value = Location.class, name = "Location"),
        @JsonSubTypes.Type(value = Observation.class, name = "Observation"),
        @JsonSubTypes.Type(value = ObservedProperty.class, name = "ObservedProperty"),
        @JsonSubTypes.Type(value = Sensor.class, name = "Sensor"),
        @JsonSubTypes.Type(value = Thing.class, name = "Thing") })
public abstract class Sensorthing<SensorthingT extends Sensorthing<SensorthingT>> {

    /**
     * Unique identifier of the entity.
     */
    public final String id;
    /**
     * Links to this entity in the Sensorthings database. We use f-bounded quantification to ensure
     * that this always links to an entity of the same type as the type of the class. Assume we have
     * a class Datastream, then this will have to be something akin to
     * SingleLocalLink&lt;Datastream&gt;.
     */
    @JsonIgnore
    public final SingleLocalLink<SensorthingT> selfLink;

    /**
     * The url used by the {@link Sensorthing#selfLink}. Used in (de-)/serialization.
     */
    public final String selfUrl;

    /**
     * Creates a new Sensorthings object with the given id and the specified database link.
     * 
     * @param id       Unique identifier of the object that will be created.
     * @param selfUrl  Links to the Sensorthings database object.
     * @param relative Whether or not the selfUrl is relative to the Sensorthings entry point or
     *                 absolute
     */
    public Sensorthing(String id, String selfUrl, boolean relative) {
        this.id = id;
        this.selfLink = new SingleLocalLink<SensorthingT>(selfUrl, relative, this);
        this.selfUrl = selfUrl;
    }

    /**
     * Checks if to {@link Sensorthing} are equal, by checking if the id of the objects are the
     * same.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Sensorthing)) {
            return false;
        }
        Sensorthing<?> other = (Sensorthing<?>) obj;
        return Objects.equals(id, other.id);
    }

}
