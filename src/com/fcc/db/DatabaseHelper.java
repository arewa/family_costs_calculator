package com.fcc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "fcc";
	private static final int DATABASE_VERSION = 1;
	public static final String FCC_DATA_TABLE = "FCC_DATA";
	public static final String FCC_INCOME_SAVE_TABLE = "FCC_INCOME_SAVE";
	private static final String FCC_DATA_TABLE_CREATE = "CREATE TABLE " + FCC_DATA_TABLE + " (" +
			"DATE TEXT," +
			"COST TEXT," +
			"PLAN REAL," +
			"FACT REAL" +
			");";
	private static final String FCC_INCOME_SAVE_TABLE_CREATE = "CREATE TABLE " + FCC_INCOME_SAVE_TABLE + " (" +
			"DATE TEXT," +
			"INCOME REAL," +
			"SAVE REAL" +
			");";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(FCC_DATA_TABLE_CREATE);
		db.execSQL(FCC_INCOME_SAVE_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		// Just stub
	}

}
