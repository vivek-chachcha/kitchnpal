package kitchnpal.sql;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.ArrayList;

import kitchnpal.kitchnpal.Fridge;
import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.QuantityType;

/**
 * Created by Mandy on 2017-03-27.
 */

public class FridgeDatabaseTest extends AndroidTestCase {

    private FridgeDatabaseHelper db;
    private static final String USER_EMAIL = "androidtest@test.com";
    private static final String USER_PASSWORD = "123Password";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new FridgeDatabaseHelper(context);
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    public void testAddIngredient(){
        Ingredient i = new Ingredient("Ingredient 1", 100, QuantityType.CUP);
        db.addIngredient(i);
        ArrayList<Ingredient> ingredients = db.getIngredients();
        assertEquals(1, ingredients.size());
        assertEquals("Ingredient 1", ingredients.get(0).getIngredientName());
        assertEquals(100.0, ingredients.get(0).getIngredientAmount());
        assertEquals(QuantityType.CUP, ingredients.get(0).getIngredientQuantityType());
    }

    public void testUpdateIngredient(){
        Ingredient i1 = new Ingredient("Ingredient 1", 100, QuantityType.CUP);
        db.addIngredient(i1);
        ArrayList<Ingredient> ingredients = db.getIngredients();
        assertEquals(1, ingredients.size());

        Ingredient i2 = new Ingredient("Ingredient 1", 200, QuantityType.CUP);
        db.updateIngredient(i2);
        ingredients = db.getIngredients();
        assertEquals(1, ingredients.size());
        assertEquals("Ingredient 1", ingredients.get(0).getIngredientName());
        assertEquals(200.0, ingredients.get(0).getIngredientAmount());
        assertEquals(QuantityType.CUP, ingredients.get(0).getIngredientQuantityType());
    }

    public void testIngredientAmount(){
        Ingredient i1 = new Ingredient("Ingredient 1", 100, QuantityType.CUP);
        db.addIngredient(i1);
        double amount = db.getIngredientAmount("Ingredient 1");
        assertEquals(100.0, amount);
    }

    public void testIsIngredientInFridge(){
        Ingredient i1 = new Ingredient("Ingredient 1", 100, QuantityType.CUP);
        db.addIngredient(i1);
        assertTrue(db.isIngredientInFridge("Ingredient 1"));
        assertFalse(db.isIngredientInFridge("Invalid Ingredient"));
    }

    public void testRemoveIngredient(){
        Ingredient i1 = new Ingredient("Ingredient 1", 100, QuantityType.CUP);
        db.addIngredient(i1);
        ArrayList<Ingredient> ingredients = db.getIngredients();
        assertEquals(1, ingredients.size());
        db.removeIngredient(i1);
        ingredients = db.getIngredients();
        assertEquals(0, ingredients.size());
    }
}
