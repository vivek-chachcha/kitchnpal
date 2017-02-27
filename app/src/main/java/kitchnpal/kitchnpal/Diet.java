package kitchnpal.kitchnpal;

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
}
