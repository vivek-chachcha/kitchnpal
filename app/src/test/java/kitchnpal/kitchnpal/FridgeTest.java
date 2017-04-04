package kitchnpal.kitchnpal;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mandy on 2017-03-28.
 */

public class FridgeTest {

    @Test
    public void testCanGetFridge() throws Exception {
        Fridge f = Fridge.getInstance();
        assertEquals(Fridge.class, f.getClass());
    }

    @Test
    public void testAddIngredientToFridge() throws Exception {
        Fridge f = Fridge.getInstance();
        f.clearFridge();
        Ingredient i1 = new Ingredient("Ingredient A", 100, QuantityType.GRAMS);
        Ingredient i2 = new Ingredient("Ingredient B", 100, QuantityType.GRAMS);
        f.addIngredient(i1);
        f.addIngredient(i2);
        assertEquals(2, f.getIngredients().size());
        assertEquals(i1.getIngredientName(), f.getIngredients().get(0).getIngredientName());
        assertEquals(i2.getIngredientName(), f.getIngredients().get(1).getIngredientName());
    }

    @Test
    public void testAddDuplicateIngredientToFridge() throws Exception {
        Fridge f = Fridge.getInstance();
        f.clearFridge();
        Ingredient i1 = new Ingredient("Ingredient A", 100, QuantityType.GRAMS);
        Ingredient i2 = new Ingredient("Ingredient A", 100, QuantityType.GRAMS);
        f.addIngredient(i1);
        f.addIngredient(i2);
        assertEquals(1, f.getIngredients().size());
        assertEquals(i1.getIngredientName(), f.getIngredients().get(0).getIngredientName());
    }

    @Test
    public void testAddNullIngredientToFridge() throws Exception {
        Fridge f = Fridge.getInstance();
        f.clearFridge();
        Ingredient i1 = new Ingredient("Ingredient A", 100, QuantityType.GRAMS);
        f.addIngredient(i1);
        f.addIngredient(null);
        assertEquals(1, f.getIngredients().size());
    }
}
