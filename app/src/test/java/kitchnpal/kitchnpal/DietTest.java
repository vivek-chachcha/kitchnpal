package kitchnpal.kitchnpal;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DietTest {

    @Test
    public void testDietSize() throws Exception {
        List<String> diets = Diet.stringValues();
        assertEquals(5, diets.size());
    }

    @Test
    public void testStringToPescetarian() throws Exception {
        Diet d = Diet.stringToDiet("Pescetarian");
        assertEquals(Diet.PESCETARIAN, d);
        assertEquals("Pescetarian", Diet.PESCETARIAN.getName());
    }

    @Test
    public void testStringToLactoVegetarian() throws Exception {
        Diet d = Diet.stringToDiet("Lacto Vegetarian");
        assertEquals(Diet.LACTO_VEGETARIAN, d);
        assertEquals("Lacto Vegetarian", Diet.LACTO_VEGETARIAN.getName());
    }

    @Test
    public void testStringToOvoVegetarian() throws Exception {
        Diet d = Diet.stringToDiet("Ovo Vegetarian");
        assertEquals(Diet.OVO_VEGETARIAN, d);
        assertEquals("Ovo Vegetarian", Diet.OVO_VEGETARIAN.getName());
    }

    @Test
    public void testStringToVegan() throws Exception {
        Diet d = Diet.stringToDiet("Vegan");
        assertEquals(Diet.VEGAN, d);
        assertEquals("Vegan", Diet.VEGAN.getName());
    }

    @Test
    public void testStringToVegetarian() throws Exception {
        Diet d = Diet.stringToDiet("Vegetarian");
        assertEquals(Diet.VEGETARIAN, d);
        assertEquals("Vegetarian", Diet.VEGETARIAN.getName());
    }

    @Test
    public void testEmptyStringToDiet() throws Exception {
        Diet d = Diet.stringToDiet("");
        assertNull(d);
    }

    @Test
    public void testInvalidStringToDiet() throws Exception {
        Diet d = Diet.stringToDiet("Invalid Test");
        assertNull(d);
    }

    @Test
    public void testNullToDiet() throws Exception {
        Diet d = Diet.stringToDiet(null);
        assertNull(d);
    }
}