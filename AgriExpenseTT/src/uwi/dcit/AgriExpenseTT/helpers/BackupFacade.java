package uwi.dcit.AgriExpenseTT.helpers;

import android.database.sqlite.SQLiteDatabase;

import uwi.dcit.AgriExpenseTT.models.CloudKeyContract;
import uwi.dcit.AgriExpenseTT.models.CycleContract;
import uwi.dcit.AgriExpenseTT.models.CycleResourceContract;
import uwi.dcit.AgriExpenseTT.models.RedoLogContract;
import uwi.dcit.AgriExpenseTT.models.ResourceContract;
import uwi.dcit.AgriExpenseTT.models.ResourcePurchaseContract;
import uwi.dcit.AgriExpenseTT.models.TransactionLogContract;
import uwi.dcit.AgriExpenseTT.models.UpdateAccountContract;

/**
 * Created by Tony on 11/24/2016.
 */

public class BackupFacade implements DbActions{

    private final String TBL_SUFFIX;

    public BackupFacade(String s){
        TBL_SUFFIX = s;
    }

    public void createDb(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE " + ResourceContract.ResourceEntry.TABLE_NAME + " RENAME TO " + ResourceContract.ResourceEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("ALTER TABLE " + CycleContract.CycleEntry.TABLE_NAME + " RENAME TO " + CycleContract.CycleEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("ALTER TABLE " + ResourcePurchaseContract.ResourcePurchaseEntry.TABLE_NAME + " RENAME TO " + ResourcePurchaseContract.ResourcePurchaseEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("ALTER TABLE " + CycleResourceContract.CycleResourceEntry.TABLE_NAME + " RENAME TO " + CycleResourceContract.CycleResourceEntry.TABLE_NAME + TBL_SUFFIX );
        // db.execSQL("ALTER TABLE " + LabourEntry.TABLE_NAME + "RENAME TO " + LabourEntry.TABLE_NAME + TBL_SUFFIX );

        db.execSQL("ALTER TABLE " + CloudKeyContract.CloudKeyEntry.TABLE_NAME + " RENAME TO " + CloudKeyContract.CloudKeyEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("ALTER TABLE " + RedoLogContract.RedoLogEntry.TABLE_NAME + " RENAME TO " + RedoLogContract.RedoLogEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("ALTER TABLE " + TransactionLogContract.TransactionLogEntry.TABLE_NAME + " RENAME TO " + TransactionLogContract.TransactionLogEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("ALTER TABLE " + UpdateAccountContract.UpdateAccountEntry.TABLE_NAME + " RENAME TO " + UpdateAccountContract.UpdateAccountEntry.TABLE_NAME + TBL_SUFFIX );

    }

    public void insertData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + ResourceContract.ResourceEntry.TABLE_NAME + "(" + ResourceContract.ResourceEntry._ID + ", name, type)  SELECT _id, name, type FROM  " + ResourceContract.ResourceEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("INSERT INTO " + CycleContract.CycleEntry.TABLE_NAME + "(" + CycleContract.CycleEntry._ID + ", cropId, landType, landAmt, cycledate, tspent, hType, hAmt, costPer, county, cropName ) SELECT _id, cropId, landType, landAmt, cycledate, tspent, hType, hAmt, costPer, county, cropName FROM " + CycleContract.CycleEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("INSERT INTO " + ResourcePurchaseContract.ResourcePurchaseEntry.TABLE_NAME + "(" + ResourcePurchaseContract.ResourcePurchaseEntry._ID + ", rId, type, quantifier, qty, cost, remaining, date, resource)  SELECT _id, rId, type, quantifier, qty, cost, remaining, date, resource FROM " + ResourcePurchaseContract.ResourcePurchaseEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("INSERT INTO " + CycleResourceContract.CycleResourceEntry.TABLE_NAME + "(" + CycleResourceContract.CycleResourceEntry._ID + ", pId, type, qty, quantifier, cycleId, useCost) SELECT _id, pId, type, qty, quantifier, cycleId, useCost FROM  " + CycleResourceContract.CycleResourceEntry.TABLE_NAME + TBL_SUFFIX );
        // db.execSQL("INSERT INTO " + LabourEntry.TABLE_NAME + "(" + LabourEntry._ID  + ", labour, name) SELECT id, labour, name FROM  " + LabourEntry.TABLE_NAME + TBL_SUFFIX );

        db.execSQL("INSERT INTO " + CloudKeyContract.CloudKeyEntry.TABLE_NAME + "(" + CloudKeyContract.CloudKeyEntry._ID + ", key, ctable, rowid ) SELECT _id, key, ctable, rowid  FROM " + CloudKeyContract.CloudKeyEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("INSERT INTO " + RedoLogContract.RedoLogEntry.TABLE_NAME + "(" + RedoLogContract.RedoLogEntry._ID + ", redotable, row_id, operation)  SELECT _id, redotable, row_id, operation FROM " + RedoLogContract.RedoLogEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("INSERT INTO " + TransactionLogContract.TransactionLogEntry.TABLE_NAME + "(" + TransactionLogContract.TransactionLogEntry._ID + ", transtable, rowid, operation, transtime)  SELECT _id, transtable, rowid, operation, transtime FROM  " + TransactionLogContract.TransactionLogEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("INSERT INTO " + UpdateAccountContract.UpdateAccountEntry.TABLE_NAME + "(" + UpdateAccountContract.UpdateAccountEntry._ID + ", acc, county, address, lastUpdated, signedIn, cloudKey)  SELECT _id, acc, county, address, lastUpdated, signedIn, cloudKey FROM " + UpdateAccountContract.UpdateAccountEntry.TABLE_NAME + TBL_SUFFIX );
    }

    public void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + ResourceContract.ResourceEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("DROP TABLE IF EXISTS " + CycleContract.CycleEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("DROP TABLE IF EXISTS " + ResourcePurchaseContract.ResourcePurchaseEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("DROP TABLE IF EXISTS " + CycleResourceContract.CycleResourceEntry.TABLE_NAME + TBL_SUFFIX );
        // db.execSQL("DROP TABLE IF EXISTS " + LabourEntry.TABLE_NAME + TBL_SUFFIX );

        db.execSQL("DROP TABLE IF EXISTS " + CloudKeyContract.CloudKeyEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("DROP TABLE IF EXISTS " + RedoLogContract.RedoLogEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("DROP TABLE IF EXISTS " + TransactionLogContract.TransactionLogEntry.TABLE_NAME + TBL_SUFFIX );
        db.execSQL("DROP TABLE IF EXISTS " + UpdateAccountContract.UpdateAccountEntry.TABLE_NAME + TBL_SUFFIX );
    }


}

