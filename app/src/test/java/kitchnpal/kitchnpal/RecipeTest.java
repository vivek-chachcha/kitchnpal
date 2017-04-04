package kitchnpal.kitchnpal;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Mandy on 2017-03-27.
 */

public class RecipeTest {

    private Ingredient i1 = new Ingredient("Ingredient A", 400, QuantityType.UNIT);
    private Ingredient i2 = new Ingredient("Ingredient B", 200, QuantityType.CUPS);
    private Ingredient i3 = new Ingredient("Ingredient C", 100, QuantityType.GRAMS);

    private String instruction1 = "Instruction 1";
    private String instruction2 = "Instruction 2";
    private String instruction3 = "Instruction 3";

    @Test
    public void testRecipeCreated() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(i1);
        ingredients.add(i2);
        ingredients.add(i3);
        ArrayList<String> instructions = new ArrayList<>();
        instructions.add(instruction1);
        instructions.add(instruction2);
        instructions.add(instruction3);
        Recipe r = new Recipe("Recipe 1", 1, ingredients, instructions);
        assertEquals(1, r.getId());
        assertEquals("Recipe 1", r.getName());
        assertEquals(ingredients, r.getIngredients());
        assertEquals(instructions, r.getInstructions());
    }

    @Test
    public void testEmptyIngredientInstruction() {
        Recipe r = new Recipe("Recipe 1", 1);
        assertEquals(1, r.getId());
        assertEquals("Recipe 1", r.getName());
        assertNull(r.getIngredients());
        assertNull(r.getInstructions());
    }

    @Test
    public void testMissedIngredientCount() {
        Recipe r = new Recipe("Recipe 1", 1, 3);
        assertEquals(1, r.getId());
        assertEquals("Recipe 1", r.getName());
        assertEquals(3, r.getMissedIngredientCount());
    }

    @Test
    public void testChangeInstructions() {
        String instruction4 = "Instruction 4";
        String instruction5 = "Instruction 5";
        String instruction6 = "Instruction 6";

        ArrayList<String> instructions = new ArrayList<>();
        instructions.add(instruction4);
        instructions.add(instruction5);
        instructions.add(instruction6);
        Recipe r = new Recipe("Recipe 1", 1);
        r.setInstructions(instructions);
        assertEquals(1, r.getId());
        assertEquals("Recipe 1", r.getName());
        assertEquals(instructions, r.getInstructions());
    }

    @Test
    public void testChangeIngredients() {
        Ingredient i4 = new Ingredient("Ingredient D", 400, QuantityType.UNIT);
        Ingredient i5 = new Ingredient("Ingredient E", 200, QuantityType.CUPS);
        Ingredient i6 = new Ingredient("Ingredient F", 100, QuantityType.GRAMS);

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(i4);
        ingredients.add(i5);
        ingredients.add(i6);
        Recipe r = new Recipe("Recipe 1", 1);
        r.setIngredients(ingredients);
        assertEquals(1, r.getId());
        assertEquals("Recipe 1", r.getName());
        assertEquals(ingredients, r.getIngredients());
    }


}
