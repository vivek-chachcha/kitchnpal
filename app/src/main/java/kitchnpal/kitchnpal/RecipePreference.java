package kitchnpal.kitchnpal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mandy on 2017-03-08.
 */

public enum RecipePreference {
    LOWCAL("Lowest Calories"),
    CHEAPEST("Lowest Price");

    private String name;

    RecipePreference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> stringValues() {
        List<String> prefs = new ArrayList<>();
        for (RecipePreference r : values()) {
            prefs.add(r.getName());
        }
        return prefs;
    }

    public static RecipePreference stringToPreference(String s) {
        if (s == null) {
            return null;
        }
        switch (s) {
            case "Lowest Calories":
                return LOWCAL;
            case "Lowest Price":
                return CHEAPEST;
            default:
                return null;
        }
    }
}
