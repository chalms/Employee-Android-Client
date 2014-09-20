package web;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class WebRequest {
	private String url; 
	private AsyncHttpResponseHandler handler; 
	public CallbackWrapper callbackWrapper; 
	public RequestWrapper requestWrapper; 
	private ProgressDisplay progressDisplay = null;
	
	public WebRequest(String u, AsyncHttpResponseHandler h) {
		setUrl(u); setHandler(h); 
		this.createRequestWrapper();
	}

	public WebRequest(String u, AsyncHttpResponseHandler h, CallbackWrapper c) {
		setUrl(u);
		setHandler(h);
		callbackWrapper = c; 
		createRequestWrapper();
	}
	
	void createRequestWrapper() {
		if (callbackWrapper == null) {
			callbackWrapper = new CallbackWrapper() {
				@Override
				public void render() {
					System.out.println("Ran default callback wrapper");
					return; 
				}
			};
		}
		requestWrapper = new RequestWrapper(callbackWrapper) {
			@Override
			public CallbackWrapper executeRequest() {
				int x = WebClient.get(url, handler);
				if (x == 1) {
					return callback;
				} else {
					return null;
				}
			}
		};
	}
	
	public void setRequestWrapper(RequestWrapper k) {
		requestWrapper = k; 
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
	
//	public void setToken(Token t) {
//		this.token = t; 
//		this.setAuthenticationHeader(token);
//	}
//	
//	public void sendLoginNotification() {
//		
//	}
//	
//	public void signIn(JSONObject params, String url) throws UnsupportedEncodingException, JSONException, InvalidParametersException {
//		setTokenHandler(new TokenHandler(context, params, url, this )); 
//	}
}
