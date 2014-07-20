package web;



import util.JsonReader;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class WebRequest {
	private String url; 
	private AsyncHttpResponseHandler handler; 
	private JsonReader reader; 
	
	private ProgressDisplay progressDisplay = null; 
	
	public WebRequest(String u, AsyncHttpResponseHandler h) {
			setUrl(u); setHandler(h); 
	}
	public WebRequest(String u, AsyncHttpResponseHandler h, ProgressDisplay p) {
		setUrl(u); setHandler(h); setProgressDisplay(p); 
	}
	public WebRequest(String u, AsyncHttpResponseHandler h, JsonReader j) {
		setUrl(u); setHandler(h); setReader(j); 
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public AsyncHttpResponseHandler getHandler() {
		return handler;
	}
	public void setHandler(AsyncHttpResponseHandler handler) {
		this.handler = handler;
	}
	public ProgressDisplay getProgressDisplay() {
		return progressDisplay;
	}
	public void setProgressDisplay(ProgressDisplay progressDisplay) {
		this.progressDisplay = progressDisplay;
	}
	public JsonReader getReader() {
		return reader;
	}
	public void setReader(JsonReader reader) {
		this.reader = reader;
	}
	
//	public void setToken(Token t) {
//		this.token = t; 
//		this.setAuthenticationHeader(token);
//	}
//	
//	public void sendLoginNotification() {
//		this.main.getMainController().loggedIn();
//		sendLoginNotification();
//	}
//	
//	public void signIn(JSONObject params, String url) throws UnsupportedEncodingException, JSONException, InvalidParametersException {
//		setTokenHandler(new TokenHandler(context, params, url, this )); 
//	}
}
