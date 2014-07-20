package web;

public class ProgressDisplay {
	boolean display = false; 
	String message = "";
	ProgressDisplay(String m, Boolean d ){
		display = d; 
		if (display) message = m; 
	}
}
