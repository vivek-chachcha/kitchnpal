package kitchnpal.kitchnpal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2017-02-25.
 */

public class Ingredient {
    private String ingredientName;
    private double ingredientAmount;

    private QuantityType quantityType;

    public Ingredient(String name, double amount,  QuantityType type) {
        ingredientName = name;
        ingredientAmount = amount;
        quantityType = type;
    }

    public String ingredientToString() {
        return this.getIngredientName() + " x " + this.getIngredientAmount() + " " + this.getQuantityTypeString();
    }

    public String getIngredientName() {
        return this.ingredientName;
    }
    
    public QuantityType getIngredientQuantityType() {
        return this.quantityType;
    }

    public void setIngredientName(String name) {
        this.ingredientName = name;
    }

    public double getIngredientAmount() {
        return this.ingredientAmount;
    }

    public void setIngredientAmount(float amount) {
        this.ingredientAmount = amount;
    }
    
    public void setQuantityType(QuantityType qt) {
        this.quantityType = qt;
    }
    
    public String getQuantityTypeString() {
        return this.quantityType.getName();
    }

    public static List<String> ingredientsToString(List<Ingredient> ingredients) {
        if (ingredients != null) {
            List<String> ingredientsString = new ArrayList<String>(ingredients.size());
            for (Ingredient i : ingredients) {
                ingredientsString.add(i.ingredientToString());
            }
            return ingredientsString;
        }
        else return null;
    }
}
