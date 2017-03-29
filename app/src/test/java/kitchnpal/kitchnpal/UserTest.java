package kitchnpal.kitchnpal;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Mandy on 2017-03-27.
 */

public class UserTest {

    @Test
    public void testUserRecipe() {
        User user = User.getInstance();
        Recipe r = new Recipe("Recipe A", 1);
        user.setCurrentRecipe(r);
        assertEquals(r, user.getRecipe());
    }

    @Test
    public void testUserSearchResults() {
        User user = User.getInstance();
        user.clearSearchResults();
        Recipe r = new Recipe("Recipe A", 1);
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(r);
        user.setSearchResults(recipes);
        assertEquals(1, user.getSearchResults().size());
        assertEquals(1, user.getSearchResults().get(0).getId());
    }

    @Test
    public void testAccessToken() {
        User user = User.getInstance();
        assertNull(user.getAccessToken());
        user.setAccessToken("ABC123Token");
        assertEquals("ABC123Token", user.getAccessToken());
    }

    @Test
    public void testName() {
        User user = User.getInstance();
        assertNull(user.getName());
        user.setName("UserName");
        assertEquals("UserName", user.getName());
    }

    @Test
    public void testPassword() {
        User user = User.getInstance();
        assertNull(user.getPassword());
        user.setPassword("Password123");
        assertEquals("Password123", user.getPassword());
    }

    @Test
    public void testEmail() {
        User user = User.getInstance();
        assertNull(user.getEmail());
        user.setEmail("Email@123.com");
        assertEquals("Email@123.com", user.getEmail());
    }

    @Test
    public void testAddDietRestrictions() {
        User user = User.getInstance();
        user.clearDietRestrictions();
        assertNotNull(user.getDietRestrictions());
        user.addDietRestriction(Diet.LACTO_VEGETARIAN);
        assertEquals(1, user.getDietRestrictions().size());
    }

    @Test
    public void testAddDietRestrictionsDuplicate() {
        User user = User.getInstance();
        user.clearDietRestrictions();
        assertNotNull(user.getDietRestrictions());
        user.addDietRestriction(Diet.LACTO_VEGETARIAN);
        assertEquals(1, user.getDietRestrictions().size());
        user.addDietRestriction(Diet.OVO_VEGETARIAN);
        user.addDietRestriction(Diet.LACTO_VEGETARIAN);
        assertEquals(2, user.getDietRestrictions().size());
    }

    @Test
    public void testAddDietRestrictionsNull() {
        User user = User.getInstance();
        user.clearDietRestrictions();
        assertNotNull(user.getDietRestrictions());
        user.addDietRestriction(Diet.LACTO_VEGETARIAN);
        assertEquals(1, user.getDietRestrictions().size());
        user.addDietRestriction(null);
        assertEquals(1, user.getDietRestrictions().size());
    }

    @Test
    public void testNumCalPerDay() {
        User user = User.getInstance();
        assertNull(user.getNumCalPerDay());
        user.setNumCalPerDay(Integer.valueOf(123));
        assertEquals(Integer.valueOf(123), user.getNumCalPerDay());
    }

    @Test
    public void testPreference() {
        User user = User.getInstance();
        assertNull(user.getPreference());
        user.setPreference(RecipePreference.CHEAPEST);
        assertEquals(RecipePreference.CHEAPEST, user.getPreference());
    }

    @Test
    public void testFavourites() {
        User user = User.getInstance();
        Recipe r = new Recipe("Recipe 1", 1);
        user.addFavourite(r);
        assertEquals(r, user.getFavourites().get(0));
        assertEquals(1, user.getFavourites().size());
    }

    @Test
    public void testRemoveFavourites() {
        User user = User.getInstance();
        Recipe r = new Recipe("Recipe 1", 1);
        user.removeFavourite(r);
        assertEquals(0, user.getFavourites().size());
    }

    @Test
    public void testAddAllergies() {
        User user = User.getInstance();
        user.clearAllergies();
        assertNotNull(user.getAllergies());
        user.addAllergy(Intolerance.TREE_NUT);
        assertEquals(Intolerance.TREE_NUT, user.getAllergies().get(0));
        assertEquals(1, user.getAllergies().size());
    }

    @Test
    public void testAddAllergiesDuplicate() {
        User user = User.getInstance();
        user.clearAllergies();
        assertNotNull(user.getAllergies());
        user.addAllergy(Intolerance.TREE_NUT);
        assertEquals(1, user.getAllergies().size());
        user.addAllergy(Intolerance.TREE_NUT);
        user.addAllergy(Intolerance.SHELLFISH);
        assertEquals(2, user.getAllergies().size());
    }

    @Test
    public void testAddAllergiesNull() {
        User user = User.getInstance();
        user.clearAllergies();
        assertNotNull(user.getAllergies());
        user.addAllergy(Intolerance.SULFITE);
        assertEquals(1, user.getAllergies().size());
        user.addAllergy(null);
        assertEquals(1, user.getAllergies().size());
    }
}
