package de.visaq;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link RestConstants}.
 */
public class RestConstantsTest {
    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<RestConstants> constructor = RestConstants.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void entryPointTest() {
        assertNotNull(RestConstants.ENTRY_POINT);
    }
}
