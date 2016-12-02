package uwi.dcit.AgriExpenseTT.models;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class    CountyContract {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INT_TYPE = " INTEGER";
	
	private static final String COMMA_SEP = ",";
	public static final String SQL_CREATE_COUNTIES = 
			"CREATE TABLE IF NOT EXISTS "+CountyEntry.TABLE_NAME + " ( "+
					CountyEntry._ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT "+ COMMA_SEP +
					CountyEntry.COLUMN_NAME_COUNTRY + INT_TYPE + COMMA_SEP + 
					CountyEntry.COLUMN_NAME_COUNTY + TEXT_TYPE + COMMA_SEP + 
					" FOREIGN KEY ("+ CountyEntry.COLUMN_NAME_COUNTRY +") REFERENCES "+
					CountryContract.CountryEntry.TABLE_NAME + "(" + CountryContract.CountryEntry._ID + "));";

	public static final String SQL_DELETE_COUNTIES = ""
			+ "DROP TABLE IF EXISTS " + CountyEntry.TABLE_NAME;

	/*
	public static void createTable(SQLiteDatabase db){
		db.execSQL(CountyContract.SQL_CREATE_COUNTIES);
	}

	public static void dropTable(SQLiteDatabase db){
		db.execSQL(CountyContract.SQL_DELETE_COUNTIES);
	}
	*/
	public static final String [][] counties = {
		{"Trinidad and Tobago", "St George"},
		{"Trinidad and Tobago", "St David"},
		{"Trinidad and Tobago", "Caroni"},
		{"Trinidad and Tobago", "St Andrew"},
		{"Trinidad and Tobago", "Victoria"},
		{"Trinidad and Tobago", "Nariva"},
		{"Trinidad and Tobago", "St Patrick"},
		{"Trinidad and Tobago", "Mayaro"},
		{"St Lucia", "Anse la Raye"},
		{"St Lucia", "Castries"},
		{"St Lucia", "Choiseul"},
		{"St Lucia", "Dauphin"},
		{"St Lucia", "Dennery"},
		{"St Lucia", "Gros Islet"},
		{"St Lucia", "Laborie"},
		{"St Lucia", "Micoud"},
		{"St Lucia", "Praslin"},
		{"St Lucia", "Soufrière"},
		{"St Lucia", "Vieux Fort"},
		{"Jamaica", "Clarendon"},
		{"Jamaica", "Hanover"},
		{"Jamaica", "Kingston"},
		{"Jamaica", "Manchester"},
		{"Jamaica", "Portland"},
		{"Jamaica", "St Ann"},
		{"Jamaica", "St Andrew"},
		{"Jamaica", "St Catherine"},
		{"Jamaica", "St Elizabeth"},
		{"Jamaica", "St James"},
		{"Jamaica", "St Mary"},
		{"Jamaica", "St Thomas"},
		{"Jamaica", "Trelawny"},
		{"Jamaica", "Westmoreland"},
		{"Anguilla", "Blowing Point"},
		{"Anguilla", "East End"},
		{"Anguilla", "George Hill"},
		{"Anguilla", "Island Harbour"},
		{"Anguilla", "North Hill"},
		{"Anguilla", "North Side"},
		{"Anguilla", "Sandy Ground"},
		{"Anguilla", "Sandy Hill"},
		{"Anguilla", "South Hill"},
		{"Anguilla", "Stoney Ground"},
		{"Anguilla", "The Farrington"},
		{"Anguilla", "The Quarter"},
		{"Anguilla", "The Valley"},
		{"Anguilla", "West End"},
		{"Antigua & Barbuda", "St George"},
		{"Antigua & Barbuda", "St John"},
		{"Antigua & Barbuda", "St Mary"},
		{"Antigua & Barbuda", "St Paul"},
		{"Antigua & Barbuda", "St Peter"},
		{"Antigua & Barbuda", "St Phillip"},
		{"Antigua & Barbuda", "Barbuda"},
		{"Antigua & Barbuda", "Redonda"},
		{"Bahamas", "Bimini"},
		{"Bahamas", "New Providence"},
		{"Bahamas", "Grand Bahama"},
		{"Bahamas", "Andros"},
		{"Bahamas", "Spanish Wells"},
		{"Barbados", "Christ Church"},
		{"Barbados", "St James"},
		{"Barbados", "St Lucy"},
		{"Barbados", "St Michael"},
		{"Barbados", "St Peter"},
		{"Barbados", "St Thomas"},
		{"Barbados", "St Andrew"},
		{"Barbados", "St George"},
		{"Barbados", "St Joseph"},
		{"Barbados", "St Phillip"},
		{"Barbados", "St John"},
		{"Belize", "Belize"},
		{"Belize", "Cayo"},
		{"Belize", "Orange Walk"},
		{"Belize", "Corozal"},
		{"Belize", "Stan Creek"},
		{"Belize", "Toledo"},
		{"British Virgin Islands", "Tortola"},
		{"British Virgin Islands", "Virgin Gorda"},
		{"British Virgin Islands", "Anegada"},
		{"British Virgin Islands", "Jost Van Dyke"},
		{"Cayman Islands", "Grand Cayman"},
		{"Cayman Islands", "Little Cayman"},
		{"Cayman Islands", "Cayman Brac"},
		{"Dominica", "St George"},
		{"Dominica", "St John"},
		{"Dominica", "St Peter"},
		{"Dominica", "St Joseph"},
		{"Dominica", "St Paul"},
		{"Dominica", "St Luke"},
		{"Dominica", "St Mark"},
		{"Dominica", "St Patrick"},
		{"Dominica", "St David"},
		{"Dominica", "St Andrew"},
		{"Grenada", "St Andrew"},
		{"Grenada", "St David"},
		{"Grenada", "St George"},
		{"Grenada", "St John"},
		{"Grenada", "St Mark"},
		{"Grenada", "St Patrick"},
		{"Grenada", "Carriacou"},
		{"Grenada", "Petite Martinuque"},
		{"Guyana","Barima-Waini"},
		{"Guyana","Pomeroon-Supenaam"},
		{"Guyana","Essequibo Islands-West Demerara"},
		{"Guyana","Demerara-Mahaica"},
		{"Guyana","Mahaica-Berbice"},
		{"Guyana","East Berbice-Corentyne"},
		{"Guyana","Cuyuni-Mazaruni"},
		{"Guyana","Potaro-Siparuni"},
		{"Guyana","Upper Takutu-Upper Essequibo"},
		{"Guyana","Upper Demerara-Berbice"},
		{"Haiti","Nord"},
		{"Haiti","Nord Quest"},
		{"Haiti","Nord Est"},
		{"Haiti","Artibontie"},
		{"Haiti","Centre"},
		{"Haiti","Quest"},
		{"Haiti","Sud Est"},
		{"Haiti","Grand Anse"},
		{"Haiti","Sud"},
		{"Montserrat","St Peter"},
		{"Montserrat","St Anthony"},
		{"Montserrat","St Georges"},
		{"St Kitts & Nevis","Nevis"},
		{"St Kitts & Nevis","St George"},
		{"St Kitts & Nevis","St Thomas"},
		{"St Kitts & Nevis","St Anne"},
		{"St Kitts & Nevis","St Paul"},
		{"St Kitts & Nevis","St John"},
		{"St Kitts & Nevis","Christ Church"},
		{"St Kitts & Nevis","St Mary"},
		{"St Kitts & Nevis","St Peter"},
		{"St Vincent and the Grenadines", "Charlotte"},
		{"St Vincent and the Grenadines", "Northern Grenadines"},
		{"St Vincent and the Grenadines", "Southern Grenadines"},
		{"St Vincent and the Grenadines", "St Andrew"},
		{"St Vincent and the Grenadines", "St David"},
		{"St Vincent and the Grenadines", "St George"},
		{"St Vincent and the Grenadines", "St Patrick"},
		{"Suriname", "Brokopondo"},
		{"Suriname", "Commewijne"},
		{"Suriname", "Coronie"},
		{"Suriname", "Marowijne"},
		{"Suriname", "Nickerie"},
		{"Suriname", "Para"},
		{"Suriname", "Paramaribo"},
		{"Suriname", "Saramacca"},
		{"Suriname", "Sipaliwini"},
		{"Suriname", "Wanica"},
		{"Turks & Caicos Islands", "Providenciales"},
		{"Turks & Caicos Islands", "Grand Turk Island"},
		{"Turks & Caicos Islands", "Caicos Islands"}
	};
	
	
	public static abstract class CountyEntry implements BaseColumns{
		public static final String TABLE_NAME = "counties";
		public static final String COLUMN_NAME_COUNTRY = "country";
		public static final String COLUMN_NAME_COUNTY = "county";
	}
}
