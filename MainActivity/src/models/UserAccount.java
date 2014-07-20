package models;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import errors.InvalidParametersException;

public class UserAccount {
		private String email = null;
		private String password = null;
		private JSONObject params = null; 
		private String url = null; 

		public UserAccount(JSONObject p, String u) throws JSONException, InvalidParametersException {
			url = u; 
			params = p; 
			setEmail(params.getString("email"));
			setPassword(params.getString("password"));
		}

		public StringEntity asStringEntity() throws JSONException,
				UnsupportedEncodingException {
			return new StringEntity(params.toString());
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}
		
		public String getUrl() {
			return url; 
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public boolean isValid() {
			return ((email != null) && (password != null));
		}
}
