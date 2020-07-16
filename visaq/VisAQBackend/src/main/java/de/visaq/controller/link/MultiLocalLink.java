package de.visaq.controller.link;

import java.util.ArrayList;

import de.visaq.controller.SensorthingController;
import de.visaq.model.sensorthings.Sensorthing;

/**
 * Encapsulates a Sensorthings query that can return multiple Sensorthings entities and provides
 * caching functionality.
 *
 * @param <SensorthingT> A class that extends Sensorthings
 */
public class MultiLocalLink<SensorthingT extends Sensorthing<SensorthingT>>
        extends MultiNavigationLink<SensorthingT> {
    public final ArrayList<SensorthingT> cachedSensorthing;

    /**
     * Constructs a new MultiLocalLink with a query that possibly returns multiple Sensorthings
     * entities and caches the results.
     * 
     * @param url               {@link NavigationLink#NavigationLink(String, boolean)}
     * @param relative          {@link NavigationLink#NavigationLink(String, boolean)}
     * @param cachedSensorthing The retrieved entities of this query
     */
    public MultiLocalLink(String url, boolean relative, ArrayList<SensorthingT> cachedSensorthing) {
        super(url, relative);
        this.cachedSensorthing = cachedSensorthing;
    }

    @Override
    public ArrayList<SensorthingT> get(SensorthingController<SensorthingT> controller) {
        return cachedSensorthing;
    }

}
