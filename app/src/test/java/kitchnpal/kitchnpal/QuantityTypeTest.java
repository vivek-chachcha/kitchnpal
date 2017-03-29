package kitchnpal.kitchnpal;

import org.junit.Test;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Jerry on 2017-03-26.
 */

public class QuantityTypeTest {

    @Test
    public void testQuantityTypeSize() throws Exception {
        List<String> types = QuantityType.stringValues();
        assertEquals(10, types.size());
    }

    @Test
    public void testStringToUnit() throws Exception {
        QuantityType q = QuantityType.stringToType("unit(s)");
        assertEquals(QuantityType.UNIT, q);
        assertEquals("unit(s)", QuantityType.UNIT.getName());

        q = QuantityType.stringToType("units");
        assertEquals(QuantityType.UNIT, q);
        assertEquals("unit(s)", QuantityType.UNIT.getName());

        q = QuantityType.stringToType("unit");
        assertEquals(QuantityType.UNIT, q);
        assertEquals("unit(s)", QuantityType.UNIT.getName());
    }

    @Test
    public void testStringToLbs() throws Exception {
        QuantityType q = QuantityType.stringToType("lb");
        assertEquals(QuantityType.LB, q);
        assertEquals("lb", QuantityType.LB.getName());

        q = QuantityType.stringToType("lbs");
        assertEquals(QuantityType.LB, q);
        assertEquals("lb", QuantityType.LB.getName());

        q = QuantityType.stringToType("pounds");
        assertEquals(QuantityType.LB, q);
        assertEquals("lb", QuantityType.LB.getName());

        q = QuantityType.stringToType("pound");
        assertEquals(QuantityType.LB, q);
        assertEquals("lb", QuantityType.LB.getName());
    }

    @Test
    public void testStringToTeaspoon() throws Exception {
        QuantityType q = QuantityType.stringToType("teaspoon");
        assertEquals(QuantityType.TEASPOON, q);
        assertEquals("teaspoon", QuantityType.TEASPOON.getName());
    }

    @Test
    public void testStringToTeaspoons() throws Exception {
        QuantityType q = QuantityType.stringToType("teaspoons");
        assertEquals(QuantityType.TEASPOONS, q);
        assertEquals("teaspoons", QuantityType.TEASPOONS.getName());
    }

    @Test
    public void testStringToTablespoons() throws Exception {
        QuantityType q = QuantityType.stringToType("tablespoons");
        assertEquals(QuantityType.TABLESPOONS, q);
        assertEquals("tablespoons", QuantityType.TABLESPOONS.getName());
    }

    @Test
    public void testStringToGram() throws Exception {
        QuantityType q = QuantityType.stringToType("gram");
        assertEquals(QuantityType.GRAM, q);
        assertEquals("gram", QuantityType.GRAM.getName());
    }

    @Test
    public void testStringToCup() throws Exception {
        QuantityType q = QuantityType.stringToType("cup");
        assertEquals(QuantityType.CUP, q);
        assertEquals("cup", QuantityType.CUP.getName());
    }

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
