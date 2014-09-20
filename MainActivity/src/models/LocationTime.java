package models;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationTime {
	double longitude;
	double latitude; 
	String time; 
	
	public LocationTime(double longi, double lati, String t) {
		longitude = longi; 
		latitude = lati; 
		time = t; 
	}
	
	public double getLongitude(){
		return longitude; 
	}
	
	public double getLatitude(){
		return latitude; 
	}
	
	public String getTime() {
		return time;
	}
	
	public static LocationTime build(JSONObject params) {
		String time;
		try {
			time = params.getString("time");
			double longitude = params.getDouble("longitude"); 
			double latitude = params.getDouble("latitude"); 
			return new LocationTime(longitude, latitude, time); 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}

}
