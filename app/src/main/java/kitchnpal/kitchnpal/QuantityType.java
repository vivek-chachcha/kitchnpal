package kitchnpal.kitchnpal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2017-03-05.
 */

public enum QuantityType {

        UNIT("Unit(s)"),
        LBS("LB(S)"),
        CUP("Cup(s)"),
        OUNCE("Ounce(s)"),
        GRAMS("Gram(s)");

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

    public static QuantityType stringToDiet(String s) {
        switch(s) {
            case "Unit(s)":
                return UNIT;
            case "LB(S)":
                return LBS;
            case "Cup(s)":
                return CUP;
            case "Ounce(s)":
                return OUNCE;
            case "Gram(s)":
                return GRAMS;
            default:
                return null;
        }
    }
}