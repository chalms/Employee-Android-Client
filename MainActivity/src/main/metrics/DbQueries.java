package main.metrics;

import java.util.ArrayList;

public class DbQueries {
	
	public static String end = "\n"; 
	public static String tab = "\t"; 
	
	public static String checkIfTableExists(String tableName, String schemaName, String intermediant) {
		return  "IF (EXISTS (SELECT * " + end
					+ tab + "FROM INFORMATION_SCHEMA " + end 
					+ tab + "WHERE TABLE_SCHEMA = " + schemaName + end 
					+ tab + "AND TABLE_NAME = " + tableName + "))" + end 
				    + "BEGIN" + end 
					+ intermediant + end
					+ "END" + end; 
	}
	
	public static String createTable(String tableName, ArrayList <String> columns ) {
		String exec = "CREATE TABLE " + tableName + end 
					+ "(" + end; 
		for (String column : columns) {
			exec = exec + column + end; 
		}
		exec = exec + ");" + end; 
		return exec; 
	}
	
	public static ArrayList <String> createLocationColumns() {
		ArrayList <String> locationColumns = new ArrayList<String> (); 
		locationColumns.add("DOUBLE(10, 5) LONGITUTUDE"); 
		locationColumns.add("DOUBLE(10, 5) LATITUTUDE "); 
		locationColumns.add(" LATITUTUDE "); 
		
		
	}
}
