package kitchnpal.kitchnpal;

import java.util.List;

/**
 * Created by Mandy on 2017-02-26.
 */

public class User {

    private String name;
    private String password;
    private String email;
    private List<Diet> dietRestrictions;
    private int numCalPerDay;
    private List<String> preferences;
    private List<Recipe> favourites;
    private List<Intolerance> allergies;

    public User(String name, String password, String email, List<Diet> dietRestrictions,
                int numCalPerDay, List<String> preferences, List<Recipe> favourites, List<Intolerance> allergies) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.dietRestrictions = dietRestrictions;
        this.numCalPerDay = numCalPerDay;
        this.preferences = preferences;
        this.favourites = favourites;
        this.allergies = allergies;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Diet> getDietRestrictions() {
        return dietRestrictions;
    }

    public int getNumCalPerDay() {
        return numCalPerDay;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public List<Recipe> getFavourites() {
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

    public void addDietRestriction(Diet dietRestriction) {
        this.dietRestrictions.add(dietRestriction);
    }

    public void setNumCalPerDay(int numCalPerDay) {
        this.numCalPerDay = numCalPerDay;
    }

    public void addPreference(String preference) {
        this.preferences.add(preference);
    }

    public void addFavourite(Recipe favourite) {
        this.favourites.add(favourite);
    }

    public void addAllergy(Intolerance allergy) {
        this.allergies.add(allergy);
    }
}
