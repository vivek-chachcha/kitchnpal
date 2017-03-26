package kitchnpal.kitchnpal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mandy on 2017-02-26.
 */

public enum Diet {
    PESCETARIAN("Pescetarian"),
    LACTO_VEGETARIAN("Lacto Vegetarian"),
    OVO_VEGETARIAN("Ovo Vegetarian"),
    VEGAN("Vegan"),
    VEGETARIAN("Vegetarian");

    private String name;

    Diet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> stringValues() {
        List<String> diets = new ArrayList<>();
        for (Diet d : values()) {
            diets.add(d.getName());
        }
        return diets;
    }

    public static Diet stringToDiet(String s) {
        if (s == null) {
            return null;
        }
        switch(s) {
            case "Pescetarian":
                return PESCETARIAN;
            case "Lacto Vegetarian":
                return LACTO_VEGETARIAN;
            case "Ovo Vegetarian":
                return OVO_VEGETARIAN;
            case "Vegan":
                return VEGAN;
            case "Vegetarian":
                return VEGETARIAN;
            default:
                return null;
        }
    }
}
