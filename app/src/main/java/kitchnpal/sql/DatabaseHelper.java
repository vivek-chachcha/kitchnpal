package kitchnpal.sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mandy on 2017-03-03.
 */

public abstract class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "KitchnPal.db";

    /***************** Strings for Fridge DB *******************/
    protected static final String TABLE_FRIDGE = "fridge";
    protected static final String COLUMN_INGREDIENT_NAME = "ingredient_name";
    protected static final String COLUMN_INGREDIENT_AMOUNT = "ingredient_amount";
    protected static final String COLUMN_INGREDIENT_QUANTITY_TYPE = "ingredient_quantity_type";

    protected String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + TABLE_FRIDGE + "("
            + COLUMN_INGREDIENT_NAME + " TEXT PRIMARY KEY,"
            + COLUMN_INGREDIENT_AMOUNT + " TEXT,"
            + COLUMN_INGREDIENT_QUANTITY_TYPE + " TEXT"
            + ")";

    protected String DROP_INGREDIENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_FRIDGE;

    /***************** Strings for User DB *******************/
    protected static final String TABLE_USER = "user";
    protected static final String COLUMN_USER_NAME = "user_name";
    protected static final String COLUMN_USER_EMAIL = "user_email";
    protected static final String COLUMN_USER_PASSWORD = "user_password";
    protected static final String COLUMN_DIET_RESTRICTIONS = "user_diet";
    protected static final String COLUMN_NUM_CAL_PER_DAY = "user_cal";
    protected static final String COLUMN_ALLERGIES = "user_allergy";
    protected static final String COLUMN_PREFERENCE = "user_recipe_preference";
    protected static final String COLUMN_FAVOURITES = "user_favourite";
    protected static final String COLUMN_ACCESS_TOKEN = "user_access_token";
    protected String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_EMAIL + " TEXT PRIMARY KEY,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_DIET_RESTRICTIONS + " TEXT,"
            + COLUMN_NUM_CAL_PER_DAY + " INT,"
            + COLUMN_ALLERGIES + " TEXT,"
            + COLUMN_PREFERENCE + " TEXT,"
            + COLUMN_FAVOURITES + " TEXT,"
            + COLUMN_ACCESS_TOKEN + " TEXT"
            + ")";
    protected String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DatabaseHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, null, DB_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_INGREDIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        sqLiteDatabase.execSQL(DROP_INGREDIENT_TABLE);
        onCreate(sqLiteDatabase);
    }
}
