package kitchnpal.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.QuantityType;


/**
 * Created by Jerry on 2017-03-08.
 */

public class FridgeDatabaseHelper extends DatabaseHelper {

    public FridgeDatabaseHelper(Context context) {
        super(context);
    }

    public FridgeDatabaseHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, errorHandler);
    }

    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_INGREDIENT_NAME, ingredient.getIngredientName());
        values.put(COLUMN_INGREDIENT_AMOUNT, ingredient.getIngredientAmount());
        values.put(COLUMN_INGREDIENT_QUANTITY_TYPE, ingredient.getQuantityTypeString());

        db.insert(TABLE_FRIDGE, null, values);
        db.close();
    }

    public void updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_INGREDIENT_NAME + " = ?";
        String[] selectionArgs = {String.valueOf(ingredient.getIngredientName())};

        ContentValues values = new ContentValues();
        values.put(COLUMN_INGREDIENT_AMOUNT, ingredient.getIngredientAmount());

        db.update(TABLE_FRIDGE, values, selection, selectionArgs);
        db.close();
    }

    public double getIngredientAmount(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_INGREDIENT_NAME + " = ?";
        String[] selectionArgs = {String.valueOf(name)};

        String[] columns = { COLUMN_INGREDIENT_AMOUNT };
        Cursor cursor = db.query(TABLE_FRIDGE,  // table to query
                columns,                      // columns to return
                selection,                    // columns for the WHERE clause
                selectionArgs,                // values for the WHERE clause
                null,                         // group the rows
                null,                         // filter by row groups
                null);                        // sort order

        double value = 0;
        if (cursor !=  null) {
            cursor.moveToFirst();
            value = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return value;
    }

    public boolean isIngredientInFridge(String ingredientName){
        String[] columns = { COLUMN_INGREDIENT_NAME };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_INGREDIENT_NAME + " = ?";
        String[] selectionArgs = {ingredientName};
        Cursor cursor = db.query(TABLE_FRIDGE,  // table to query
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

    public void removeIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_INGREDIENT_NAME + " = ?";
        String[] selectionArgs = {String.valueOf(ingredient.getIngredientName())};

        db.delete(TABLE_FRIDGE, selection, selectionArgs);
        db.close();
    }

    public ArrayList<Ingredient> getIngredients() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FRIDGE,  // table to query
                null,                      // columns to return
                null,                    // columns for the WHERE clause
                null,                // values for the WHERE clause
                null,                         // group the rows
                null,                         // filter by row groups
                null);                        // sort order

        cursor.moveToFirst();
        ArrayList<Ingredient> results = new ArrayList<Ingredient>();

        while(!cursor.isAfterLast()) {
            Ingredient ing = new Ingredient(cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT_NAME)),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT_AMOUNT))),
                            QuantityType.stringToType(cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT_QUANTITY_TYPE))));
            results.add(ing);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return results;
    }
}
