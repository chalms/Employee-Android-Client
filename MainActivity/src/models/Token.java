package models;

import org.json.JSONException;
import org.json.JSONObject;


public class Token {
	private String token = null;
	public double ttl = 0;
	private Integer userId = null;
	

	boolean isValid() {
		return ((ttl < 10) || (token == null));
	}

	public Token(JSONObject theToken) throws JSONException {
		JSONObject apiSessionToken = theToken.getJSONObject("api_session_token");
		token = apiSessionToken.getString("token");
		ttl = apiSessionToken.getDouble("ttl");
		setUserId(apiSessionToken.getInt("user_id"));
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}
