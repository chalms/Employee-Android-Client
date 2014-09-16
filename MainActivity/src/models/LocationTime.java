package models;

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
		

}
