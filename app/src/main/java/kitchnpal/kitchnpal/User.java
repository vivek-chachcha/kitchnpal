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
    private RecipePreference preference;
    private ArrayList<Recipe> favourites = new ArrayList<>();
    private List<Intolerance> allergies = new ArrayList<>();
    private Recipe currentRecipe;
    private ArrayList<Recipe> searchResults = new ArrayList<>();
    private String accessToken;

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public RecipePreference getPreference() {
        return preference;
    }

    public ArrayList<Recipe> getFavourites() {
        return favourites;
    }

    public List<Intolerance> getAllergies() {
        return allergies;
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
    
    public Recipe getRecipe() { return this.currentRecipe; }

    public ArrayList<Recipe> getSearchResults() {
        return this.searchResults;
    }

    public void addDietRestriction(Diet dietRestriction) {
        if (dietRestriction != null && !this.dietRestrictions.contains(dietRestriction)) {
            this.dietRestrictions.add(dietRestriction);
        }
    }

    public void clearDietRestrictions() {
        this.dietRestrictions = new ArrayList<>();
    }

    public void setNumCalPerDay(Integer numCalPerDay) {
        this.numCalPerDay = numCalPerDay;
    }

    public void setPreference(RecipePreference preference) {
        this.preference = preference;
    }

    public void addFavourite(Recipe favourite) {
        if (favourite != null && !favourite.equals("") && !this.favourites.contains(favourite)) {
            this.favourites.add(favourite);
        }
    }

    public void removeFavourite(Recipe favourite) {
        if (favourite != null) {
            for (int i = 0; i< this.favourites.size(); i++) {
                if (this.favourites.get(i).getId() == favourite.getId()) {
                    this.favourites.remove(i);
                }
            }
        }
    }

    public void addAllergy(Intolerance allergy) {
        if (allergy != null && !this.allergies.contains(allergy)) {
            this.allergies.add(allergy);
        }
    }

    public void clearAllergies() {
        this.allergies = new ArrayList<>();
    }
    
    public void setCurrentRecipe(Recipe r) {
        this.currentRecipe = r;        
    }

    public void setSearchResults(ArrayList<Recipe> results) {
        this.searchResults = results;
    }

    public void clearSearchResults() {
        this.searchResults = new ArrayList<>();

    }
}
