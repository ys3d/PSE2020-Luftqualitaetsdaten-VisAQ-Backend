package de.visaq.controller.link;

import org.json.JSONObject;

import de.visaq.controller.SensorthingController;
import de.visaq.model.sensorthings.Sensorthing;

/**
 * Encapsulates a Sensorthings query that can return a single Sensorthings entity.
 *
 * @param <SensorthingT> A class that extends Sensorthings
 */
public class SingleOnlineLink<SensorthingT extends Sensorthing<SensorthingT>>
        extends SingleNavigationLink<SensorthingT> {
    private SingleLocalLink<SensorthingT> cache;

    /**
     * Constructs a new SingleLocalLink with a query that returns a Sensorthings entity.
     * 
     * @param url      {@link NavigationLink#NavigationLink(String, boolean)}
     * @param relative {@link NavigationLink#NavigationLink(String, boolean)}
     */
    public SingleOnlineLink(String url, boolean relative) {
        super(url, relative);
    }

    @Override
    public Sensorthing<SensorthingT> get(SensorthingController<SensorthingT> controller) {
        if (cache != null) {
            return cache.get(controller);
        }

        JSONObject response = getJson();

        if (response == null) {
            return null;
        }

        SensorthingT built = controller.singleBuild(response);

        cache = new SingleLocalLink<SensorthingT>(url, false, built);

        return built;
    }
}
