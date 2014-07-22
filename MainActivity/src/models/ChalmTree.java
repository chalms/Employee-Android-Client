package models;

import java.util.HashMap;
import java.util.Iterator;

public class ChalmTree {
	public HashMap <String, ChalmTree> nextRoute = new HashMap <String, ChalmTree>(); 
	public HashMap <String, Integer> routeElements = new HashMap <String, Integer> (); 
	public String className; 
	ChalmTree (String c) {className = c;}
	void print() {
		
		System.out.println("_______className________");
		Iterator<String> iter = routeElements.keySet().iterator(); 
		while(iter.hasNext()) {
			String next = iter.next(); 
			System.out.println("\t:" + next + " " + routeElements.get(next).toString());
			iter.remove();
		}
		System.out.println(" ");
		iter = routeElements.keySet().iterator(); 
		while(iter.hasNext()) {
			String next = iter.next(); 
			System.out.println("\t\t ---> " +  next );
		}
		iter.remove();
		System.out.println("_____________________");
	}
}
