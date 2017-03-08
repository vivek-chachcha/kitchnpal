package kitchnpal.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import kitchnpal.kitchnpal.Diet;
import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.Intolerance;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.User;
import kitchnpal.servicerequest.MakeRequest;

public class UserDatabaseHelper extends DatabaseHelper {

    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_DIET_RESTRICTIONS = "user_diet";
    private static final String COLUMN_NUM_CAL_PER_DAY = "user_cal";
    private static final String COLUMN_ALLERGIES = "user_allergy";
    private static final String COLUMN_PREFERENCE = "user_recipe_preference";
    private static final String COLUMN_FAVOURITES = "user_favourite";
    private static final String COLUMN_FRIDGE = "user_fridge";
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_EMAIL + " TEXT PRIMARY KEY,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_DIET_RESTRICTIONS + " TEXT,"
            + COLUMN_NUM_CAL_PER_DAY + " INT,"
            + COLUMN_ALLERGIES + " TEXT,"
            + COLUMN_PREFERENCE + " TEXT,"
            + COLUMN_FAVOURITES + " TEXT"
            + ")";
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public UserDatabaseHelper(Context context) {
        super(context);
    }

    public UserDatabaseHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void updateUserName(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(user.getEmail())};

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());

        db.update(TABLE_USER, values, selection, selectionArgs);
        db.close();
    }

    public void updateUserDietRestrictions(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(user.getEmail())};

        ContentValues values = new ContentValues();
        values.put(COLUMN_DIET_RESTRICTIONS, convertDietRestrictions(user.getDietRestrictions()));

        db.update(TABLE_USER, values, selection, selectionArgs);
        db.close();
    }

    public void updateUserCalories(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(user.getEmail())};

        ContentValues values = new ContentValues();
        values.put(COLUMN_NUM_CAL_PER_DAY, user.getNumCalPerDay());

        db.update(TABLE_USER, values, selection, selectionArgs);
        db.close();
    }

    public void updateUserAllergies(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(user.getEmail())};

        ContentValues values = new ContentValues();
        values.put(COLUMN_ALLERGIES, convertAllergies(user.getAllergies()));

        db.update(TABLE_USER, values, selection, selectionArgs);
        db.close();
    }

    public void updateUserPreference(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(user.getEmail())};

        ContentValues values = new ContentValues();
        values.put(COLUMN_PREFERENCE, user.getPreference());

        db.update(TABLE_USER, values, selection, selectionArgs);
        db.close();
    }

    public void updateUserFavourites(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(user.getEmail())};

        ContentValues values = new ContentValues();
        values.put(COLUMN_FAVOURITES, convertFavourites(user.getFavourites()));

        db.update(TABLE_USER, values, selection, selectionArgs);
        db.close();
    }

    public void updateFridge(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(user.getEmail())};

        ContentValues values = new ContentValues();
        values.put(COLUMN_FRIDGE, convertFridgeIngredients(user.getFridgeIngredients()));
        db.update(TABLE_USER, values, selection, selectionArgs);
        db.close();
    }

    private String convertFavourites(List<Recipe> favourites) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < favourites.size(); i++) {
            stringBuilder.append(favourites.get(i).getName());
            if (i != favourites.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private String convertFridgeIngredients(List<Ingredient> ingredients) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            stringBuilder.append(ingredients.get(i).getIngredientName());
            if (i != ingredients.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private String convertDietRestrictions(List<Diet> diets) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < diets.size(); i++) {
            stringBuilder.append(diets.get(i).getName());
            if (i != diets.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private String convertAllergies(List<Intolerance> intolerances) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < intolerances.size(); i++) {
            stringBuilder.append(intolerances.get(i).getName());
            if (i != intolerances.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public boolean checkUser(String email) {
        String[] columns = { COLUMN_USER_EMAIL };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USER,  // table to query
                columns,                      // columns to return
                selection,                    // columns for the WHERE clause
                selectionArgs,                // values for the WHERE clause
                null,                         // group the rows
                null,                         // filter by row groups
                null);                        // sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public boolean checkUserPassword(String email, String password) {
        String[] columns = { COLUMN_USER_EMAIL };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, // table to query
                columns,                     // columns to return
                selection,                   // columns for the WHERE clause
                selectionArgs,               // values for the WHERE clause
                null,                        // group the rows
                null,                        // filter by row groups
                null);                       // sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public String getDietRestrictions(String email) {
        return retrieveDataInColumn(COLUMN_DIET_RESTRICTIONS, email);
    }

    public String getUserName(String email) {
        return retrieveDataInColumn(COLUMN_USER_NAME, email);
    }

    public String getAllergies(String email) {
        return retrieveDataInColumn(COLUMN_ALLERGIES, email);
    }

    public String getUserPreferences(String email) {
        return retrieveDataInColumn(COLUMN_PREFERENCE, email);
    }

    public Integer getUserCalories(String email) {
        String[] columns = { COLUMN_NUM_CAL_PER_DAY };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USER, // table to query
                columns,                     // columns to return
                selection,                   // columns for the WHERE clause
                selectionArgs,               // values for the WHERE clause
                null,                        // group the rows
                null,                        // filter by row groups
                null);                       // sort order
        Integer value = null;
        System.out.println(cursor.getCount());
        if (cursor !=  null) {
            cursor.moveToFirst();
            value = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return value;
    }

    private String retrieveDataInColumn(String columnName, String email) {
        String[] columns = {columnName};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USER, // table to query
                columns,                     // columns to return
                selection,                   // columns for the WHERE clause
                selectionArgs,               // values for the WHERE clause
                null,                        // group the rows
                null,                        // filter by row groups
                null);                       // sort order
        String value = "";
        System.out.println(cursor.getCount());
        if (cursor != null) {
            cursor.moveToFirst();
            value = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return value;
    }

    public ArrayList<Recipe> getFavourites(String email) {
        String[] columns = { COLUMN_FAVOURITES };
        String selection = COLUMN_USER_EMAIL;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USER,  // table to query
                columns,                      // columns to return
                selection,                    // columns for the WHERE clause
                selectionArgs,                // values for the WHERE clause
                null,                         // group the rows
                null,                         // filter by row groups
                null);                        // sort order
        String[] results = cursor.getString(0).split(",");
        ArrayList<Recipe> array = new ArrayList<>();
        MakeRequest mr = new MakeRequest();
        for (String r : results) {
            array.add(mr.fullRecipeCache.get(r));
        }
        cursor.close();
        db.close();
        return array;
    }

    public String[] getFridgeIngredients(String email) {
        String[] columns = { COLUMN_FRIDGE };
        String selection = COLUMN_USER_EMAIL;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USER,  // table to query
                columns,                      // columns to return
                selection,                    // columns for the WHERE clause
                selectionArgs,                // values for the WHERE clause
                null,                         // group the rows
                null,                         // filter by row groups
                null);                        // sort order
        String[] results = cursor.getString(0).split(",");
        cursor.close();
        db.close();
        return results;
    }
}
