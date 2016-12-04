package uwi.dcit.AgriExpenseTT.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import uwi.dcit.AgriExpenseTT.models.CycleContract;
import uwi.dcit.AgriExpenseTT.models.CycleResourceContract;

public class DbVersion172 extends DbVersion {


    private static final int VERSION = 174;
    private static final String DATABASE_NAME = "agriDb";
    private static final String TAG_NAME = "AgriExpenseDBHelper";
    private Context ctx;
    private SQLiteDatabase db;
    private int ver;
    public DbVersion172(Context context, SQLiteDatabase db, int version) {
        super(context);
        this.ctx = context;
        this.db = db;
        this.ver = version;




			db.beginTransaction();

			db.execSQL("ALTER TABLE " + CycleContract.CycleEntry.TABLE_NAME + " ADD COLUMN "+ CycleContract.CycleEntry.CROPCYCLE_NAME + " TEXT");
			// Place the resource name as the default name of the cycle
			updateCycleCropName(db);

			// Place the update date as the date for the previously created resources purchased
			updatePurchaseRecs(db);

			// Add Date Column to CycleResource
			db.execSQL("ALTER TABLE " + CycleResourceContract.CycleResourceEntry.TABLE_NAME + " ADD COLUMN "+ CycleResourceContract.CycleResourceEntry.CYCLE_DATE_USED +  " TIMESTAMP");
			updateCycleResource(db);

			db.setTransactionSuccessful();
			db.endTransaction();

    }
}
