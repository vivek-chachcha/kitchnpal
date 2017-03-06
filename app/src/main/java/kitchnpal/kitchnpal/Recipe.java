package kitchnpal.kitchnpal;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 2017-02-23.
 */
public class Recipe {
    private String recipeName;
    private ArrayList<String> ingredients;
    private ArrayList<String> instructions;
    private ImageView recipeImage;

    public Recipe(String name, ArrayList<String> ingreds, ArrayList<String> instructs) {
        recipeName = name;
        ingredients = ingreds;
        instructions = instructs;
    }

    public String getName() { return recipeName; }
    public List<String> getIngredients() { return ingredients; }
    public List<String> getInstructions() { return instructions; }

    public void setImage(ImageView iw) {
        recipeImage = iw;
    }

    public void displayRecipe() {
        for (int i = 0; i< instructions.size(); i++) {
            //Do Something with each instruction
        }
    }
}
