package kitchnpal.kitchnpal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mandy on 2017-02-26.
 */

public class User {

    private static User user;
    private String name;
    private String password;
    private String email;
    private List<Diet> dietRestrictions = new ArrayList<>();
    private Integer numCalPerDay;
    private String preference;
    private List<Recipe> favourites = new ArrayList<>();
    private List<Intolerance> allergies = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();

    private User() {
        // make constructor private for singleton
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
            return user;
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<Diet> getDietRestrictions() {
        return dietRestrictions;
    }

    public Integer getNumCalPerDay() {
        return numCalPerDay;
    }

    public String getPreference() {
        return preference;
    }

    public List<Recipe> getFavourites() {
        return favourites;
    }

    public List<Intolerance> getAllergies() {
        return allergies;
    }

    public List<Ingredient> getFridgeIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addDietRestriction(Diet dietRestriction) {
        this.dietRestrictions.add(dietRestriction);
    }

    public void setNumCalPerDay(Integer numCalPerDay) {
        this.numCalPerDay = numCalPerDay;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public void addFavourite(Recipe favourite) {
        this.favourites.add(favourite);
    }

    public void addAllergy(Intolerance allergy) {
        this.allergies.add(allergy);
    }
}
