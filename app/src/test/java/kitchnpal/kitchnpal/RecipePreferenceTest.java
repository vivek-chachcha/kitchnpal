package kitchnpal.kitchnpal;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Mandy on 2017-03-27.
 */

public class RecipePreferenceTest {

    @Test
    public void testRecipePreferenceSize() throws Exception {
        List<String> preferences = RecipePreference.stringValues();
        assertEquals(2, preferences.size());
    }

    @Test
    public void testStringToPreference() throws Exception {
        RecipePreference rp = RecipePreference.stringToPreference("Lowest Calories");
        assertEquals(RecipePreference.LOWCAL, rp);
        assertEquals("Lowest Calories", RecipePreference.LOWCAL.getName());
    }

    @Test
    public void testStringToCheapest() throws Exception {
        RecipePreference rp = RecipePreference.stringToPreference("Lowest Price");
        assertEquals(RecipePreference.CHEAPEST, rp);
        assertEquals("Lowest Price", RecipePreference.CHEAPEST.getName());
    }

    @Test
    public void testEmptyStringToPreference() throws Exception {
        RecipePreference rp = RecipePreference.stringToPreference("");
        assertNull(rp);
    }

    @Test
    public void testInvalidStringToPreference() throws Exception {
        RecipePreference rp = RecipePreference.stringToPreference("Invalid Preference");
        assertNull(rp);
    }

    @Test
    public void testNullToPreference() throws Exception {
        RecipePreference rp = RecipePreference.stringToPreference(null);
        assertNull(rp);
    }
}
