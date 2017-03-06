package kitchnpal.sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mandy on 2017-03-03.
 */

public abstract class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "KitchnPal.db";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DatabaseHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, null, DB_VERSION, errorHandler);
    }
}
