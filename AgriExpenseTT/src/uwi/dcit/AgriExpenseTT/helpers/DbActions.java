package uwi.dcit.AgriExpenseTT.helpers;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tony on 12/01/2016.
 */

public interface DbActions {

    public void createDb(SQLiteDatabase db);
    public void insertData(SQLiteDatabase db);
    public void dropTables(SQLiteDatabase db);
}
