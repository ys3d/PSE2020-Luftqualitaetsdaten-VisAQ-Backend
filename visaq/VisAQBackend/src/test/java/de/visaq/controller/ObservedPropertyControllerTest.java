package de.visaq.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Tests {@link ObservedPropertyController}.
 */
public class ObservedPropertyControllerTest {
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
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVEDATASTREAM));
    }

    @Test
    public void testMultiObservedPropertyGet() {
        MultiOnlineLink<ObservedProperty> mol =
                new MultiOnlineLink<ObservedProperty>("/ObservedProperties?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

}
