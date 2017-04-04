package kitchnpal.kitchnpal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2017-03-05.
 */

public enum QuantityType {
    UNIT("unit(s)"),
    LB("lb(s)"),
    CUPS("cup(s)"),
    TEASPOONS("teaspoon(s)"),
    TABLESPOONS("tablespoon(s)"),
    GRAMS("gram(s)"),
    QUARTS("quart(s)");

    private String name;

    QuantityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> stringValues() {
        List<String> types = new ArrayList<>();
        for (QuantityType q : values()) {
            types.add(q.getName());
        }
        return types;
    }

    public static QuantityType stringToType(String s) {
        if (s == null) {
            return null;
        }

        switch(s) {
            case "unit(s)":
            case "unit":
            case "units":
                return UNIT;
            case "lb":
            case "pounds":
            case "pound":
            case "lbs":
            case "lb(s)":
                return LB;
            case "cup":
            case "cups":
            case "cup(s)":
                return CUPS;
            case "teaspoon":
            case "teaspoons":
            case "tsp":
            case "teaspoon(s)":
                return TEASPOONS;
            case "tablespoon":
            case "tablespoons":
            case "Tbsp":
            case "tablespoon(s)":
                return TABLESPOONS;
            case "gram":
            case "grams":
            case "gram(s)":
                return GRAMS;
            case "quart":
            case "quarts":
            case "quart(s)":
                return QUARTS;
            default:
                return null;
        }
    }
}