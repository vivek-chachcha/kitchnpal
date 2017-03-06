package kitchnpal.kitchnpal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mandy on 2017-02-26.
 */

public enum Intolerance {
    DAIRY("Dairy"),
    EGG("Egg"),
    GLUTEN("Gluten"),
    PEANUT("Peanut"),
    SESAME("Sesame"),
    SEAFOOD("Seafood"),
    SHELLFISH("Shellfish"),
    SOY("Soy"),
    SULFITE("Sulfite"),
    TREE_NUT("Tree Nut"),
    WHEAT("Wheat");

    private String name;

    Intolerance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> stringValues() {
        List<String> intolerances = new ArrayList<>();
        for (Intolerance i : values()) {
            intolerances.add(i.getName());
        }
        return intolerances;
    }

    public static Intolerance stringToIntolerance(String s) {
        switch(s) {
            case "Dairy":
                return DAIRY;
            case "Egg":
                return EGG;
            case "Gluten":
                return GLUTEN;
            case "Peanut":
                return PEANUT;
            case "Sesame":
                return SESAME;
            case "Seafood":
                return SEAFOOD;
            case "Shellfish":
                return SHELLFISH;
            case "Soy":
                return SOY;
            case "Sulfite":
                return SULFITE;
            case "Tree Nut":
                return TREE_NUT;
            case "Wheat":
                return WHEAT;
            default:
                return null;
        }
    }
}
