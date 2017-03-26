package kitchnpal.kitchnpal;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Jerry on 2017-03-26.
 */

public class QuantityTypeTest {

    // TODO add coverage for all cases
    @Test
    public void testStringToCups() throws Exception {
        QuantityType q = QuantityType.stringToType("cups");
        assertEquals(QuantityType.CUPS, q);
        assertEquals("cups", QuantityType.CUPS.getName());
    }

    @Test
    public void testStringToTablespoon() throws Exception {
        QuantityType q = QuantityType.stringToType("tablespoon");
        assertEquals(QuantityType.TABLESPOON, q);
        assertEquals("tablespoon", QuantityType.TABLESPOON.getName());
    }

    @Test
    public void testStringToGrams() throws Exception {
        QuantityType q = QuantityType.stringToType("grams");
        assertEquals(QuantityType.GRAMS, q);
        assertEquals("grams", QuantityType.GRAMS.getName());
    }


    @Test
    public void testEmptyStringToType() throws Exception {
        QuantityType q = QuantityType.stringToType("");
        assertNull(q);
    }

    @Test
    public void testInvalidStringToType() throws Exception {
        QuantityType q = QuantityType.stringToType("Invalid Test");
        assertNull(q);
    }

    @Test
    public void testNullToType() throws Exception {
        QuantityType q = QuantityType.stringToType(null);
        assertNull(q);
    }
}
