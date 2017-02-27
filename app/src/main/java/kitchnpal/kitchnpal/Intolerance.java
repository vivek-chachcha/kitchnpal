package kitchnpal.kitchnpal;

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
}
