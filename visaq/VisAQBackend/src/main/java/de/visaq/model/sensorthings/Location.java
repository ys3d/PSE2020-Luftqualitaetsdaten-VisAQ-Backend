package de.visaq.model.sensorthings;

import java.awt.Point;

import de.visaq.controller.link.MultiNavigationLink;

/**
 * <p>
 * Representation of the Locations entity in the OGC SensorThings API.
 * </p>
 * <p>
 * The Location entity locates the Thing(s) it associated with. A Thing's Location entity is defined
 * as the last known location of the Thing.
 * </p>
 * 
 * @see <a href=
 *      "https://developers.sensorup.com/docs/#locations_post">https://developers.sensorup.com/docs/#locations_post</a>
 */
public class Location extends Sensorthing<Location> {
    public final String name;
    public final String description;
    // TODO: Check if point is the only possible value
    public final Point location;
    public final MultiNavigationLink<HistoricalLocation> historicalLocationsLink;
    public final MultiNavigationLink<Thing> thingsLink;

    /**
     * Constructs a new {@link Location}.
     * 
     * @param id                      {@link Sensorthing#Sensorthings(String, String, boolean)}
     * @param selfUrl                 {@link Sensorthing#Sensorthings(String, String, boolean)}
     * @param relative                {@link Sensorthing#Sensorthings(String, String, boolean)}
     * @param name                    The name of the {@link Location}
     * @param description             The description of the {@link Location}
     * @param location                A point describing the actual location
     * @param historicalLocationsLink Links to the {@link HistoricalLocation}
     * @param thingsLink              Links to the {@link Thing}
     */
    public Location(String id, String selfUrl, boolean relative, String name, String description,
            Point location, MultiNavigationLink<HistoricalLocation> historicalLocationsLink,
            MultiNavigationLink<Thing> thingsLink) {
        super(id, selfUrl, relative);
        this.name = name;
        this.description = description;
        this.location = location;
        this.historicalLocationsLink = historicalLocationsLink;
        this.thingsLink = thingsLink;
    }

}
