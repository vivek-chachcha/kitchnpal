package kitchnpal.kitchnpal;

import java.util.List;

/**
 * Created by Jerry on 2017-02-25.
 */

public class Fridge {
    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
    }
}
