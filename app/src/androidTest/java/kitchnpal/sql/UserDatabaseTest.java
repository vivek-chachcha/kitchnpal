package kitchnpal.sql;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.ArrayList;

import kitchnpal.kitchnpal.Diet;
import kitchnpal.kitchnpal.Intolerance;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.RecipePreference;
import kitchnpal.kitchnpal.User;

/**
 * Created by Mandy on 2017-03-27.
 */

public class UserDatabaseTest extends AndroidTestCase {

    private UserDatabaseHelper db;
    private static final String USER_EMAIL = "androidtest@test.com";
    private static final String USER_PASSWORD = "123Password";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new UserDatabaseHelper(context);

        // Set up user for the rest of the tests
        User user = User.getInstance();
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        db.addUser(user);
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    public void testAddUser(){
        assertTrue(db.checkUser(USER_EMAIL));
        assertFalse(db.checkUser("Invalid Email"));
    }

    public void testCheckPassword(){
        assertTrue(db.checkUserPassword(USER_EMAIL, USER_PASSWORD));
        assertFalse(db.checkUserPassword(USER_EMAIL, "Invalid password"));
    }

    public void testUpdateUserName(){
        User user = User.getInstance();
        user.setName("DB Test");
        db.updateUserName(user);
        assertEquals("DB Test", db.getUserName(USER_EMAIL));
    }

    public void testUpdateDietRestrictions(){
        User user = User.getInstance();
        user.addDietRestriction(Diet.PESCETARIAN);
        user.addDietRestriction(Diet.OVO_VEGETARIAN);
        db.updateUserDietRestrictions(user);
        assertEquals("Pescetarian, Ovo Vegetarian", db.getDietRestrictions(USER_EMAIL));
    }

    public void testUpdateUserCalories(){
        User user = User.getInstance();
        user.setNumCalPerDay(Integer.valueOf(100));
        db.updateUserCalories(user);
        assertEquals(Integer.valueOf(100), db.getUserCalories(USER_EMAIL));
    }

    public void testUpdateUserAllergies(){
        User user = User.getInstance();
        user.addAllergy(Intolerance.DAIRY);
        user.addAllergy(Intolerance.EGG);
        db.updateUserAllergies(user);
        assertEquals("Dairy, Egg", db.getAllergies(USER_EMAIL));
    }

    public void testUpdateUserPreference(){
        User user = User.getInstance();
        user.setPreference(RecipePreference.CHEAPEST);
        db.updateUserPreference(user);
        assertEquals("Lowest Price", db.getUserPreferences(USER_EMAIL));
    }

    public void testUpdateUserAccessToken(){
        User user = User.getInstance();
        user.setAccessToken("123AccessToken");
        db.updateUserAccessToken(user);
        assertEquals("123AccessToken", db.getUserAccessToken(USER_EMAIL));
    }

    public void testUpdateUserFavourites(){
        User user = User.getInstance();
        Recipe r1 = new Recipe("Recipe 1", 1);
        Recipe r2 = new Recipe("Recipe 2", 2);
        user.addFavourite(r1);
        user.addFavourite(r2);
        db.updateUserFavourites(user);
        ArrayList<Recipe> recipes = db.getFavourites(USER_EMAIL);
        assertEquals(2, recipes.size());
        assertEquals(1, recipes.get(0).getId());
        assertEquals("Recipe 1", recipes.get(0).getName());
        assertEquals(2, recipes.get(1).getId());
        assertEquals("Recipe 2", recipes.get(1).getName());
    }
}
