package kitchnpal.kitchnpal;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Mandy on 2017-03-26.
 */

public class IntoleranceTest {

    @Test
    public void testIntoleranceSize() throws Exception {
        List<String> intolerances = Intolerance.stringValues();
        assertEquals(11, intolerances.size());
    }

    @Test
    public void testStringToDairy() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Dairy");
        assertEquals(Intolerance.DAIRY, i);
        assertEquals("Dairy", Intolerance.DAIRY.getName());
    }

    @Test
    public void testStringToEgg() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Egg");
        assertEquals(Intolerance.EGG, i);
        assertEquals("Egg", Intolerance.EGG.getName());
    }

    @Test
    public void testStringToGluten() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Gluten");
        assertEquals(Intolerance.GLUTEN, i);
        assertEquals("Gluten", Intolerance.GLUTEN.getName());
    }

    @Test
    public void testStringToPeanut() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Peanut");
        assertEquals(Intolerance.PEANUT, i);
        assertEquals("Peanut", Intolerance.PEANUT.getName());
    }

    @Test
    public void testStringToSesame() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Sesame");
        assertEquals(Intolerance.SESAME, i);
        assertEquals("Sesame", Intolerance.SESAME.getName());
    }

    @Test
    public void testStringToSeafood() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Seafood");
        assertEquals(Intolerance.SEAFOOD, i);
        assertEquals("Seafood", Intolerance.SEAFOOD.getName());
    }

    @Test
    public void testStringToShellfish() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Shellfish");
        assertEquals(Intolerance.SHELLFISH, i);
        assertEquals("Shellfish", Intolerance.SHELLFISH.getName());
    }

    @Test
    public void testStringToSoy() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Soy");
        assertEquals(Intolerance.SOY, i);
        assertEquals("Soy", Intolerance.SOY.getName());
    }

    @Test
    public void testStringToSulfite() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Sulfite");
        assertEquals(Intolerance.SULFITE, i);
        assertEquals("Sulfite", Intolerance.SULFITE.getName());
    }

    @Test
    public void testStringToTreeNut() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Tree Nut");
        assertEquals(Intolerance.TREE_NUT, i);
        assertEquals("Tree Nut", Intolerance.TREE_NUT.getName());
    }

    @Test
    public void testStringToWheat() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Wheat");
        assertEquals(Intolerance.WHEAT, i);
        assertEquals("Wheat", Intolerance.WHEAT.getName());
    }

    @Test
    public void testEmptyStringToIntolerance() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("");
        assertNull(i);
    }

    @Test
    public void testInvalidStringToIntolerance() throws Exception {
        Intolerance i = Intolerance.stringToIntolerance("Invalid");
        assertNull(i);
    }
}
