package de.visaq.controller.link;

import de.visaq.controller.SensorthingController;
import de.visaq.model.sensorthings.Sensorthing;

/**
 * Encapsulates a Sensorthings query that can return a single Sensorthings entity and provides
 * caching functionality.
 *
 * @param <SensorthingT> A class that extends Sensorthings
 */
public class SingleLocalLink<SensorthingT extends Sensorthing<SensorthingT>>
        extends SingleNavigationLink<SensorthingT> {
    public final Sensorthing<SensorthingT> cachedSensorthing;

    /**
     * Constructs a new SingleLocalLink with a query that returns a Sensorthings entity and caches
     * the result.
     * 
     * @param url               {@link NavigationLink#NavigationLink(String, boolean)}
     * @param relative          {@link NavigationLink#NavigationLink(String, boolean)}
     * @param cachedSensorthing The retrieved entity of this query
     */
    public SingleLocalLink(String url, boolean relative,
            Sensorthing<SensorthingT> cachedSensorthing) {
        super(url, relative);
        this.cachedSensorthing = cachedSensorthing;
    }

    @Override
    public Sensorthing<SensorthingT> get(SensorthingController<SensorthingT> controller) {
        return cachedSensorthing;
    }

}
