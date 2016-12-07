package uwi.dcit.AgriExpenseTT.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbVersion170 extends DbVersion {


    private static final int VERSION = 174;
    private static final String DATABASE_NAME = "agriDb";
    private static final String TAG_NAME = "AgriExpenseDBHelper";
    private Context ctx;
    private SQLiteDatabase db;
    private int ver;

    public DbVersion170(Context context, SQLiteDatabase db, int version) {
        super( context);
        this.ctx = context;
        this.db = db;
        this.ver = version;

        this.dropTables(db);
        onCreate(db);
        db.close();
    }



       // }
    }

