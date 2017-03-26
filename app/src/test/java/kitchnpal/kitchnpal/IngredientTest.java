package kitchnpal.kitchnpal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mandy on 2017-03-26.
 */

public class IngredientTest {

    private Ingredient i = new Ingredient("Ingredient", 400, QuantityType.CUP);

    @Test
    public void testIngredientToString() {
        String iStr = i.ingredientToString();
        assertEquals("Ingredient x 400.0 cup", iStr);
    }

    @Test
    public void testIngredientName() {
        String name = i.getIngredientName();
        assertEquals("Ingredient", name);
    }

    @Test
    public void testIngredientQuantityType() {
        QuantityType qt = i.getIngredientQuantityType();
        assertEquals(QuantityType.CUP, qt);
        String name = i.getQuantityTypeString();
        assertEquals("cup", name);
    }

    @Test
    public void testSetIngredientName() {
        i.setIngredientName("New Ingredient");
        assertEquals("New Ingredient", i.getIngredientName());
    }

    @Test
    public void testSetIngredientAmount() {
        i.setIngredientAmount(300);
        assertEquals((int) 300, (int) i.getIngredientAmount());
    }

    @Test
    public void testSetQuantityType() {
        i.setQuantityType(QuantityType.GRAM);
        assertEquals(QuantityType.GRAM, i.getIngredientQuantityType());
    }

    @Test
    public void testIngredientsToString() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("A", 1, QuantityType.CUP));
        ingredients.add(new Ingredient("B", 2, QuantityType.LB));
        ingredients.add(new Ingredient("C", 3, QuantityType.TABLESPOON));
        List<String> ingredientStrs = Ingredient.ingredientsToString(ingredients);
        assertEquals(3, ingredientStrs.size());
        assertEquals("A x 1.0 cup", ingredientStrs.get(0));
        assertEquals("B x 2.0 lb", ingredientStrs.get(1));
        assertEquals("C x 3.0 tablespoon", ingredientStrs.get(2));
    }
}
