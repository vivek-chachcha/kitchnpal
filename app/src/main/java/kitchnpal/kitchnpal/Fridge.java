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

    public boolean isIngredientInFridge(String name) {
        for (Ingredient i: this.ingredients) {
            if (i.getIngredientName().equals(name)){
                return true;
            }
        }
            return false;
    }

    public double getIngredientAmountByName(String name) {
        for (Ingredient i: this.ingredients) {
            if (i.getIngredientName().equals(name)){
                return i.getIngredientAmount();
            }
        }
        return 0;
    }
    public void addIngredient(Ingredient ingredient) {
        if (ingredient != null && !ingredients.contains(ingredient)) {
            this.ingredients.add(ingredient);
        }
        // If ingredient already exists, update the amount
        else if (ingredients.contains(ingredient)){
            int index = this.ingredients.indexOf(ingredient);
            Ingredient ing = this.ingredients.get(index);
            double currentAmount = ing.getIngredientAmount();
            ing.setIngredientAmount(currentAmount + ingredient.getIngredientAmount());
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
