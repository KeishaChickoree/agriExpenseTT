package uwi.dcit.AgriExpenseTT.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import uwi.dcit.AgriExpenseTT.models.CycleContract;

public class DbVersion171 extends DbVersion {


    private static final int VERSION = 174;
    private static final String DATABASE_NAME = "agriDb";
    private static final String TAG_NAME = "AgriExpenseDBHelper";
    private Context ctx;
    private SQLiteDatabase db;
    private int ver;

    public DbVersion171(Context context, SQLiteDatabase db, int version) {
        super(context);
        this.ctx = context;
        this.db = db;
        this.ver = version;

        if (!columnExists(db, CycleContract.CycleEntry.TABLE_NAME, CycleContract.CycleEntry._ID)) {

            Log.d(TAG_NAME, "Running Update of table structure");
        //    this.tableColumnModify(db);

            Log.d(TAG_NAME, "Running installation of countries");
           // this.createCountries(db); // Create if not exist so if previously created this will do nothing
          //  this.insertDefaultCountries(db);

            Log.d(TAG_NAME, "Running installation of counties");
         //   this.createCounties(db);
          //  this.insertDefaultCounties(db);

            Log.d(TAG_NAME, "Running upgrade of crop/plating material lists");
        //    this.updateCropList(db);
        }
    }
}
