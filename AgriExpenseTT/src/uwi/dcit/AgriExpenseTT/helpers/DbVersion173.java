package uwi.dcit.AgriExpenseTT.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import uwi.dcit.AgriExpenseTT.models.CloudKeyContract;
import uwi.dcit.AgriExpenseTT.models.CountryContract;
import uwi.dcit.AgriExpenseTT.models.CountyContract;
import uwi.dcit.AgriExpenseTT.models.CycleContract;
import uwi.dcit.AgriExpenseTT.models.CycleResourceContract;
import uwi.dcit.AgriExpenseTT.models.CycleResourceContract.CycleResourceEntry;
import uwi.dcit.AgriExpenseTT.models.LabourContract;
import uwi.dcit.AgriExpenseTT.models.RedoLogContract;
import uwi.dcit.AgriExpenseTT.models.RedoLogContract.RedoLogEntry;
import uwi.dcit.AgriExpenseTT.models.ResourceContract;
import uwi.dcit.AgriExpenseTT.models.ResourcePurchaseContract;
import uwi.dcit.AgriExpenseTT.models.TransactionLogContract;
import uwi.dcit.AgriExpenseTT.models.TransactionLogContract.TransactionLogEntry;
import uwi.dcit.AgriExpenseTT.models.UpdateAccountContract;

public class DbVersion173 extends DbVersion {


    private static final int VERSION = 174;
    private static final String DATABASE_NAME = "agriDb";
    private static final String TAG_NAME = "AgriExpenseDBHelper";
    private Context ctx;
    private SQLiteDatabase db;
    private int ver;
    public DbVersion173(Context context, SQLiteDatabase db, int version) {
        super(context);
        this.ctx = context;
        this.db = db;
        this.ver = version;

            dropTables(db);
            onCreate(db);
            db.close();


    }
}
