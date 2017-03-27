package kitchnpal.kitchnpal;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jerry on 2017-03-26.
 */

public class IngredientTest {

    Ingredient testIng = new Ingredient("Chicken", 3.0, QuantityType.CUPS);
    Ingredient testIng2 = new Ingredient("Salt", 5.0, QuantityType.GRAMS);

    @Test
    public void testIngredientToStringInFridge() throws Exception {
        String result = testIng.ingredientToStringInFridge();
        assertEquals("Chicken x 3.0 cups", result);
    }

    @Test
    public void testGetIngredientName() throws Exception {
        String result = testIng.getIngredientName();
        assertEquals("Chicken", result);
    }

    @Test
    public void testGetIngredientAmount() throws Exception {
        double result = testIng.getIngredientAmount();
        assertEquals(3.0, result, 0.01 );
    }

    @Test
    public void testGetIngredientQuantityType() throws Exception {
        QuantityType result = testIng.getIngredientQuantityType();
        assertEquals(QuantityType.CUPS, result);
    }

    @Test
    public void testGetQuantityTypeString() throws Exception {
        String result = testIng.getQuantityTypeString();
        assertEquals("cups", result);
    }

    @Test
    public void testIngredientsToString() throws Exception {
        List<Ingredient> list2 = Arrays.asList(testIng, testIng2);
        List<String> result = Ingredient.ingredientsToString(list2);
        assertEquals(Arrays.asList("Chicken x 3.0 cups", "Salt x 5.0 grams"), result);
    }
}
