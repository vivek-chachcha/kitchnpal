package kitchnpal.kitchnpal;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import kitchnpal.servicerequest.KitchnPalService;

/**
 * Created by matt on 2017-02-23.
 */
public class Recipe {
    private String recipeName;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> instructions;
    private int id;
    private int missedIngredientCount;

    public Recipe(String name, int id, ArrayList<Ingredient> ingreds, ArrayList<String> instructs) {
        recipeName = name;
        ingredients = ingreds;
        instructions = instructs;
        this.id = id;
    }

    public Recipe(String title, int id) {
        recipeName = title;
        this.id = id;
    }
    
    public Recipe(String title, int id, int missedIngredientCount) {
        recipeName = title;
        this.id = id;
        this.missedIngredientCount = missedIngredientCount;
    }

    public String getName() { return recipeName; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public List<String> getInstructions() { return instructions; }
    public int getId() { return id; }
    public int getMissedIngredientCount() { return missedIngredientCount; }

    public void setIngredients(ArrayList<Ingredient> ingreds) {
        this.ingredients = ingreds;
    }

    public void setInstructions(ArrayList<String> steps) {
        this.instructions = steps;
    }
}
