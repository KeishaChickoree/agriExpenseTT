package uwi.dcit.AgriExpenseTT.models;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class LabourContract {
	private static final String TEXT_TYPE = " TEXT";
	public static final String SQL_CREATE_LABOUR = "CREATE TABLE IF NOT EXISTS "+LabourEntry.TABLE_NAME+"("
			+ LabourEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ LabourEntry.LABOUR_NAME + TEXT_TYPE +");";


	public static final String SQL_DELETE_LABOUR = "DROP TABLE IF EXISTS "+ LabourEntry.TABLE_NAME;

	/*
	public static void createTable(SQLiteDatabase db){
		db.execSQL(LabourContract.SQL_CREATE_LABOUR);
	}

	public static void dropTable(SQLiteDatabase db){
		db.execSQL(LabourContract.SQL_DELETE_LABOUR);
	}
	*/
	public static abstract class LabourEntry implements BaseColumns{
		public static final String TABLE_NAME = "labour";
		public static final String LABOUR_NAME = "name";
	}
}
