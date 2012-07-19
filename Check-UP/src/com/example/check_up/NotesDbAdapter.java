package com.example.check_up;
import android.database.sqlite.*;
import android.content.Context;

public class NotesDbAdapter {
	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "_id";
	
	private static final String TAG = "NotesDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_CREATE = 
			"CREATE TABLE 'notes' ("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "title TEXT NOT NULL,"
					+ "body TEXT NOT NULL)";
	
	
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, "data", null, 2);
		}
		
		public void onCreate(SQLiteDatabase db) {
			
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
	}
}
