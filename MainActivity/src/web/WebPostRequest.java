package web;

import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class WebPostRequest extends WebRequest {
	private JSONObject params = null; 
	
	public WebPostRequest(String u, AsyncHttpResponseHandler h, CallbackWrapper c, JSONObject p) {
		super(u,h,c);
		setParams(p);
	}
	
	public void setParams(JSONObject p){
		params = p; 
	}
	
	public JSONObject getParams() {
		return params;
	}
	
	void setRequestWrapper() {
		requestWrapper = new RequestWrapper() {
			@Override
			public int executeRequest() {
				WebClient.post(getUrl(), getParams(), getHandler());
				return 0;
			}
		};
		if (callbackWrapper == null) {
			callbackWrapper = new CallbackWrapper() {
				@Override
				public void render() {
					return; 
					// TODO Auto-generated method stub
				}
			};
		}
	}
}
