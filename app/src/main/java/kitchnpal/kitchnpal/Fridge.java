package kitchnpal.kitchnpal;

import java.util.ArrayList;
import java.util.List;

import kitchnpal.sql.UserDatabaseHelper;

/**
 * Created by Jerry on 2017-02-25.
 */

public class Fridge {
    private List<Ingredient> ingredients = new ArrayList<>();
    private static Fridge fridge;

    private Fridge() {
        // make constructor private for singleton
    }

    public static Fridge getInstance() {
        if (fridge == null) {
            fridge = new Fridge();
            return fridge;
        }
        return fridge;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredient != null && !ingredients.contains(ingredient)) {
            this.ingredients.add(ingredient);
        }
    }

    public void removeIngredientByName(String name) {
        for (Ingredient i: this.ingredients) {
            if (i.getIngredientName().equals(name)){
                this.ingredients.remove(i);
            }
        }
    }
}
