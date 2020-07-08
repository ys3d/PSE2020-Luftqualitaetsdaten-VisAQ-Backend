package de.visaq.model.sensorthings;

import java.util.Map;

import de.visaq.controller.link.MultiNavigationLink;

/**
 * <p>
 * Representation of the Things entity in the OGC SensorThings API.
 * </p>
 * <p>
 * A Thing is an object of the physical world (physical Things) or the information world (virtual
 * Things) that is capable of being identified and integrated into communication networks [ITU-T
 * Y.2060]. A Thing has Locations and one or more Datastreams to collect Observations.
 * </p>
 * 
 * @see <a href=
 *      "https://developers.sensorup.com/docs/#things">https://developers.sensorup.com/docs/#things</a>
 */
public class Thing extends Sensorthing<Thing> implements SensorthingsProperties {
    public final String description;
    public final String name;
    public final MultiNavigationLink<Datastream> datastreamsLink;
    public final MultiNavigationLink<HistoricalLocation> historicalLocationsLink;
    public final MultiNavigationLink<Location> locationsLink;

    private final Map<String, Object> properties;

    /**
     * Constructs a new {@link Thing}.
     * 
     * @param id                      {@link Sensorthing#Sensorthings(String, String, boolean)}
     * @param selfUrl                 {@link Sensorthing#Sensorthings(String, String, boolean)}
     * @param relative                {@link Sensorthing#Sensorthings(String, String, boolean)}
     * @param description             The description of the {@link Thing}
     * @param name                    The name of the {@link Thing}
     * @param properties              Several properties of the {@link Sensor}
     * @param datastreamsLink         Links to the {@link Datastream}
     * @param historicalLocationsLink Links to the {@link HistoricalLocation}
     * @param locationsLink           Links to the {@link Location}
     */
    public Thing(String id, String selfUrl, boolean relative, String description, String name,
            Map<String, Object> properties, MultiNavigationLink<Datastream> datastreamsLink,
            MultiNavigationLink<HistoricalLocation> historicalLocationsLink,
            MultiNavigationLink<Location> locationsLink) {
        super(id, selfUrl, relative);
        this.description = description;
        this.name = name;
        this.properties = properties;
        this.datastreamsLink = datastreamsLink;
        this.historicalLocationsLink = historicalLocationsLink;
        this.locationsLink = locationsLink;
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
