package de.visaq.controller.link;

import java.util.ArrayList;

import org.json.JSONObject;

import de.visaq.controller.SensorthingController;
import de.visaq.model.sensorthings.Sensorthing;

/**
 * Encapsulates a Sensorthings query that can return multiple Sensorthings entities.
 *
 * @param <SensorthingT> A class that extends Sensorthings
 */
public class MultiOnlineLink<SensorthingT extends Sensorthing<SensorthingT>>
        extends MultiNavigationLink<SensorthingT> {
    private MultiLocalLink<SensorthingT> cache;

    /**
     * Constructs a new MultiLocalLink with a query that possibly returns multiple Sensorthings
     * entities.
     * 
     * @param url      {@link NavigationLink#NavigationLink(String, boolean)}
     * @param relative {@link NavigationLink#NavigationLink(String, boolean)}
     */
    public MultiOnlineLink(String url, boolean relative) {
        super(url, relative);
    }

    @Override
    public ArrayList<SensorthingT> get(SensorthingController<SensorthingT> controller) {
        if (cache != null) {
            return cache.get(controller);
        }

        JSONObject response = getJson();

        if (response == null) {
            return null;
        }

        ArrayList<SensorthingT> built = controller.multiBuild(response);

        cache = new MultiLocalLink<SensorthingT>(url, false, built);

        return built;
    }
}
