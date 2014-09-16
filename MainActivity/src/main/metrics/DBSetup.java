package main.metrics;

import java.io.File;

import android.database.DatabaseUtils;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBSetup {
	CursorFactory cursorFactory;
	static File dbFile; 
	SQLiteDatabase db;
	
	public static File getDbFile() {
		createOutputFolder(); 
		return dbFile; 	
	}
	
	public static void createOutputFolder(){
		dbFile = new File("db");
		if (!dbFile.exists()){
			System.out.println("creating directory ./db");
			boolean result = dbFile.mkdir();  
			if(result) System.out.println("./db created");  
		}
	}

	public DBSetup() {
		// TODO Auto-generated constructor stub
	   db = SQLiteDatabase.openOrCreateDatabase(getDbFile(), cursorFactory);
	}
}
