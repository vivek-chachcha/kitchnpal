package kitchnpal.kitchnpal;

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
    
    public Ingredient(String name, double amount) {
        ingredientName = name;
        ingredientAmount = amount;
        quantityType = QuantityType.CUP;
    }

    public String getIngredientName() {
        return this.ingredientName;
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
    
    public String getQuantityType() {
        return this.quantityType.toString();
    }
}
