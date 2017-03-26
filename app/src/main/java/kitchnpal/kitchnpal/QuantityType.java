package kitchnpal.kitchnpal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2017-03-05.
 */

public enum QuantityType {

        UNIT("unit(s)"),
        LB("lb"),
        CUP("cup"),
        CUPS("cups"),
        TEASPOON("teaspoon"),
        TEASPOONS("teaspoons"),
        TABLESPOON("tablespoon"),
        TABLESPOONS("tablespoons"),
        GRAM("gram"),
        GRAMS("grams");

        private String  name;

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
                return UNIT;
            case "lb":
                return LB;
            case "cup":
                return CUP;
            case "cups":
                return CUPS;
            case "teaspoon":
                return TEASPOON;
            case "teaspoons":
                return TEASPOONS;
            case "tablespoon":
                return TABLESPOON;
            case "tablespoons":
                return TABLESPOONS;
            case "gram":
                return GRAM;
            case "grams":
                return GRAMS;
            default:
                return null;
        }
    }
}