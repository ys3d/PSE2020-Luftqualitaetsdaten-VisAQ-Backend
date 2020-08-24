package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Tests {@link ObservedPropertyController}.
 */
public class ObservedPropertyControllerTest extends ResourceTest {
    private static final ObservedPropertyController CONTROLLER = new ObservedPropertyController();

    @Test
    public void testSingleObservedPropertyGet() {
        SingleOnlineLink<ObservedProperty> sol =
                new SingleOnlineLink<ObservedProperty>("/ObservedProperties?$top=1", true);
        sol.get(CONTROLLER);
        sol.get(CONTROLLER);
    }

    @Test
    public void testSingleObservedPropertyGetByDatastream() {
        assertNotNull(CONTROLLER.get(ALIVEDATASTREAM));
    }

    @Test
    public void testMultiObservedPropertyGet() {
        MultiOnlineLink<ObservedProperty> mol =
                new MultiOnlineLink<ObservedProperty>("/ObservedProperties?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

    @Test
    public void testSingleObservedPropertyGetById() {
        assertNull(CONTROLLER.get("undefined"));
        assertNotNull(CONTROLLER.get(ALIVEOBSERVEDPROPERTY.id));
        assertNotNull(CONTROLLER
                .get(new IdWrapper(ALIVEOBSERVEDPROPERTY.id)));

    }

    @Test
    public void singleBuildEmptyTest() {
        assertNull(CONTROLLER.singleBuild(EMPTYARRAY));
    }

    @Test
    public void getAllTest() {
        assertFalse(CONTROLLER.getAll().isEmpty());
    }

}
